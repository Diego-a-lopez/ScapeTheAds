package es.finders.scapetheadds.menu.leaderborad

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import es.finders.scapetheadds.AndroidRoom.HighScore
import kotlinx.coroutines.launch

class HighScoreViewModel : ViewModel() {
    private val _highScores = mutableStateListOf<HighScore>()
    var errorMessage: String by mutableStateOf("")
    val highScores: List<HighScore>
        get() = _highScores

    fun getHighScores() {
        viewModelScope.launch {
            val apiService = APIService.getInstance()
            try {
                _highScores.clear()
                _highScores.addAll(apiService.getHighScores(10))

            } catch (e: Exception) {
                errorMessage = e.message.toString()
            }
        }
    }

    fun setHighScore(highScore: HighScore) {
        viewModelScope.launch {
            val apiService = APIService.getInstance()
            try {
                apiService.setHighScore(highScore)
            } catch (e: Exception) {
                errorMessage = e.message.toString()
            }
        }
    }
}