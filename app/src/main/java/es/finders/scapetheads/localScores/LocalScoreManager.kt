package es.finders.scapetheads.localScores

import android.content.Context
import android.content.SharedPreferences
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import es.finders.scapetheads.AndroidRoom.HighScore

class LocalScoreManager(private val context: Context) {

    private val sharedPreferences: SharedPreferences by lazy {
        context.getSharedPreferences("high_scores", Context.MODE_PRIVATE)
    }

    private val gson = Gson()

    init {
        // Example scores to initialize
        val exampleScores = listOf(
            HighScore("2024-04-11", "100", "200"),
            HighScore("2024-04-10", "90", "180"),
            HighScore("2024-04-09", "80", "160"),
            HighScore("2024-04-08", "70", "60"),
            HighScore("2024-04-07", "60", "100"),
            HighScore("2024-04-06", "50", "170"),
            HighScore("2024-04-05", "40", "90"),
            HighScore("2024-04-04", "30", "50"),
            HighScore("2024-04-03", "20", "20"),
            HighScore("2024-04-02", "10", "10")
        )
        saveExampleScores(exampleScores)
    }

    private fun saveExampleScores(scores: List<HighScore>) {
        val highScoresJson = gson.toJson(scores)
        sharedPreferences.edit().putString("high_scores", highScoresJson).apply()
    }

    fun saveScore(score: HighScore) {
        val highScores = retrieveScores().toMutableList()

        // Insert the new score in the appropriate position based on the score value
        var indexToInsert = highScores.indexOfLast { it.score.toInt() < score.score.toInt() } + 1
        if (indexToInsert == -1) indexToInsert = 0
        highScores.add(indexToInsert, score)

        // Keep only the top 10 scores
        val topTenScores = highScores.take(10)

        // Save the top 10 scores
        val highScoresJson = gson.toJson(topTenScores)
        sharedPreferences.edit().putString("high_scores", highScoresJson).apply()
    }

    fun retrieveScores(): List<HighScore> {
        val highScoresJson = sharedPreferences.getString("high_scores", null)
        return if (highScoresJson != null) {
            gson.fromJson(highScoresJson, object : TypeToken<List<HighScore>>() {}.type)
        } else {
            emptyList()
        }
    }
}
