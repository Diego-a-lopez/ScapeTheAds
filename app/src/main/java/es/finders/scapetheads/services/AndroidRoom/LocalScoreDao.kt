package es.finders.scapetheads.services.AndroidRoom

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Query
import androidx.room.Upsert
import kotlinx.coroutines.flow.Flow

@Dao
interface LocalScoreDao {
    @Upsert
    suspend fun upsert(LocalScores: LocalScore): Unit

    @Delete
    suspend fun delete(localScore: LocalScore)

    @Query("SELECT * FROM localScore")
    fun getAll(): Flow<List<LocalScore>>

    @Query("SELECT * FROM localScore ORDER BY nickname ASC")
    fun getLocalScoresOrderedByNickname(): Flow<List<LocalScore>>

    @Query("SELECT * FROM localScore ORDER BY date ASC")
    fun getLocalScoresOrderedByDate(): Flow<List<LocalScore>>

    @Query("SELECT * FROM localScore ORDER BY score ASC")
    fun getLocalScoresOrderedByScore(): Flow<List<LocalScore>>

    @Query("SELECT * FROM localScore ORDER BY time ASC")
    fun getLocalScoresOrderedByTime(): Flow<List<LocalScore>>
}
