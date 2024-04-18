package es.finders.scapetheads.services.AndroidRoom

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import es.finders.scapetheadds.AndroidRoom.LocalScoreState
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.flatMapLatest
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

@OptIn(ExperimentalCoroutinesApi::class)
class LocalScoreViewModel(private val dao: LocalScoreDao) : ViewModel() {

    private val _scoreSortType = MutableStateFlow(ScoreSortType.DATE)
    private val _localScores = _scoreSortType
        .flatMapLatest { scoreSortType ->
            when (scoreSortType) {
                ScoreSortType.DATE -> dao.getLocalScoresOrderedByDate() //.getAll()
                ScoreSortType.SCORE -> dao.getLocalScoresOrderedByScore() //.getAll()
                ScoreSortType.TIME -> dao.getLocalScoresOrderedByTime() //.getAll()
            }
        }
        .stateIn(viewModelScope, SharingStarted.WhileSubscribed(), emptyList())

    private val _state = MutableStateFlow(LocalScoreState())

    val state = combine(_state, _scoreSortType, _localScores) { state, scoreSortType, localScores ->
        state.copy(
            localScores = localScores,
            scoreSortType = scoreSortType
        )
    }.stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), LocalScoreState())

    fun onEvent(event: LocalScoreEvent) {
        when (event) {
            is LocalScoreEvent.DeleteScore -> {
                viewModelScope.launch {
                    dao.delete(event.score)
                }
            }

            LocalScoreEvent.HideDialog -> {
                _state.update {
                    it.copy(
                        isAddingScore = false
                    )
                }
            }

            LocalScoreEvent.SaveScore -> {
                val Date = state.value.date
                val ScoreVal = state.value.scoreVal
                val Time = state.value.time

                if (Date.isBlank() || ScoreVal.isBlank() || Time.isBlank()) {
                    return
                }

                val LocalScore = LocalScore(
                    date = Date,
                    score = ScoreVal,
                    time = Time
                )
                viewModelScope.launch {
                    dao.upsert(LocalScore)
                }
                _state.update {
                    it.copy(
                        isAddingScore = false,
                        date = "",
                        scoreVal = "",
                        time = ""
                    )
                }
            }

            is LocalScoreEvent.SetDate -> {
                _state.update {
                    it.copy(
                        date = event.date
                    )
                }
            }

            is LocalScoreEvent.SetScoreVal -> {
                _state.update {
                    it.copy(
                        scoreVal = event.scoreVal
                    )
                }
            }

            is LocalScoreEvent.SetTime -> {
                _state.update {
                    it.copy(
                        time = event.time
                    )
                }
            }

            LocalScoreEvent.ShowDialog -> {
                _state.update {
                    it.copy(
                        isAddingScore = true
                    )
                }
            }

            is LocalScoreEvent.SortScores -> {
                _scoreSortType.value = event.scoreSortType
            }

            else -> {}
        }
    }
}
