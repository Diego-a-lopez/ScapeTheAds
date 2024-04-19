package es.finders.scapetheads.services.AndroidRoom

import androidx.room.Database
import androidx.room.RoomDatabase


@Database(
    entities = [LocalScore::class],
    version = 1
)
abstract class LocalScoreDatabase : RoomDatabase() {
    abstract val dao: LocalScoreDao
}

