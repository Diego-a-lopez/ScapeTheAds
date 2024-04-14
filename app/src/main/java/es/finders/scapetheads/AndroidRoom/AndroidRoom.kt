package es.finders.scapetheads.AndroidRoom

import android.content.Context
import androidx.room.ColumnInfo
import androidx.room.Dao
import androidx.room.Database
import androidx.room.Delete
import androidx.room.Entity
import androidx.room.Insert
import androidx.room.PrimaryKey
import androidx.room.Query
import androidx.room.Room
import androidx.room.RoomDatabase

@Entity
data class HighScoreEntity(
    @PrimaryKey(autoGenerate = true) val id: Int = 0,
    @ColumnInfo(name = "user") val user: String,
    @ColumnInfo(name = "score") val score: String,
    @ColumnInfo(name = "time") val time: String
)

@Dao
interface HighScoreDao {
    @Query("SELECT * FROM HighScoreEntity")
    fun getAll(): List<HighScoreEntity>

    @Insert
    fun insertAll(vararg highScores: HighScoreEntity)

    @Delete
    fun delete(highScore: HighScoreEntity)
}

@Database(entities = [HighScoreEntity::class], version = 1)
abstract class AppDatabase : RoomDatabase() {
    abstract fun highScoreDao(): HighScoreDao

    companion object {
        @Volatile
        private var INSTANCE: AppDatabase? = null

        fun getDatabase(context: Context): AppDatabase {
            return INSTANCE ?: synchronized(this) {
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    AppDatabase::class.java,
                    "database-name"
                ).build()
                INSTANCE = instance
                instance
            }
        }
    }
}

// Usage
fun saveHighScores(context: Context, highScores: List<HighScore>) {
    val db = AppDatabase.getDatabase(context)
    val highScoreDao = db.highScoreDao()
    val highScoreEntities =
        highScores.map { HighScoreEntity(user = it.user, score = it.score, time = it.time) }
    highScoreDao.insertAll(*highScoreEntities.toTypedArray())
}

fun retrieveHighScores(context: Context): List<HighScore> {
    val db = AppDatabase.getDatabase(context)
    val highScoreDao = db.highScoreDao()
    val highScoreEntities = highScoreDao.getAll()
    return highScoreEntities.map { HighScore(it.user, it.score, it.time) }
}


