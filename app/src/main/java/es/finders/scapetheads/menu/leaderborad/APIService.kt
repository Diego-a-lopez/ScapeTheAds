package es.finders.scapetheads.menu.leaderborad

import es.finders.scapetheads.AndroidRoom.HighScore
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Query

const val BASE_URL = "http://0.0.0.0:8000"

interface APIService {
    @GET("highscores/")
    suspend fun getHighScores(@Query("n") count: Int): List<HighScore>

    @POST("highscores/")
    suspend fun setHighScore(highScore: HighScore)

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
