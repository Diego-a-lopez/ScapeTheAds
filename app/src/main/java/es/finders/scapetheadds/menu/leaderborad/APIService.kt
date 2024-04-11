package es.finders.scapetheadds.menu.leaderborad

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import es.finders.scapetheadds.AndroidRoom.HighScore
import kotlinx.coroutines.launch
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.GET
import retrofit2.http.Query

const val BASE_URL = "http://0.0.0.0:8000"

interface APIService {
    @GET("highscores/")
    suspend fun getHighScores(@Query("n") count: Int): List<HighScore>

    companion object {
        private var apiService: APIService? = null
        fun getInstance(): APIService {
            if (apiService == null) {
                apiService = Retrofit.Builder()
                    .baseUrl(BASE_URL)
                    .addConverterFactory(GsonConverterFactory.create())
                    .build().create(APIService::class.java)
            }
            return apiService!!
        }
    }
}

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
}