package es.finders.scapetheads.services.AndroidRoom

sealed interface LocalScoreEvent {
    object SaveScore : LocalScoreEvent
    data class SetDate(val date: String) : LocalScoreEvent
    data class SetScoreVal(val scoreVal: String) : LocalScoreEvent
    data class SetTime(val time: String) : LocalScoreEvent
    object ShowDialog : LocalScoreEvent
    object HideDialog : LocalScoreEvent
    data class SortScores(val scoreSortType: ScoreSortType) : LocalScoreEvent
    data class DeleteScore(val score: LocalScore) : LocalScoreEvent
}