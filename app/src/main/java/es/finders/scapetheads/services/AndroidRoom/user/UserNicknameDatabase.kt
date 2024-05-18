package es.finders.scapetheads.services.AndroidRoom.user

import androidx.room.Database
import androidx.room.RoomDatabase

@Database(
    entities = [UserNickname::class],
    version = 1
)
abstract class UserNicknameDatabase : RoomDatabase() {
    abstract val dao: UserNicknameDao
}