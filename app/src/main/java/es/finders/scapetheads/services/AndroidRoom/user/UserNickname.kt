package es.finders.scapetheads.services.AndroidRoom.user

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class UserNickname(
    @PrimaryKey(autoGenerate = false)
    val userId: String,
    val nickname: String,
)