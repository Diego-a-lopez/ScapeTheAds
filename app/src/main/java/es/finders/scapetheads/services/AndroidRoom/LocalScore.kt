package es.finders.scapetheads.services.AndroidRoom

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class LocalScore(
    val date: String,
    val score: String,
    val time: String,
    @PrimaryKey(autoGenerate = true)
    val id: Int = 0
)