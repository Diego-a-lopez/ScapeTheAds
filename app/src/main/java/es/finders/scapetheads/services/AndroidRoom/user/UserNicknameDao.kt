package es.finders.scapetheads.services.AndroidRoom.user

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Query
import androidx.room.Upsert
import kotlinx.coroutines.flow.Flow

@Dao
interface UserNicknameDao {
    @Upsert
    suspend fun upsert(userNickname: UserNickname): Unit

    @Delete
    suspend fun delete(userNickname: UserNickname)

    @Query("SELECT * FROM UserNickname")
    fun getAll(): Flow<List<UserNickname>>

    @Query("SELECT * FROM usernickname WHERE userId=:id")
    fun getByUserId(id: String): UserNickname

    @Query("SELECT EXISTS(SELECT * FROM usernickname WHERE userId=:id)")
    fun userExists(id: String): Boolean
}