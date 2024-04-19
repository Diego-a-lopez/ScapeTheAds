package es.finders.scapetheads.services.APIService

import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Path

interface ApiService {

    @GET("highscores/{username}")
    fun getScoreByUserName(@Path("username") userName: String): Call<HighScore>

    @GET("highscores/")
    fun getAllHighScores(): Call<List<HighScore>>

    @POST("highscores/")
    suspend fun setHighScore(highScore: HighScore): Call<Void>
}