package es.finders.scapetheadds.AndroidRoom

import es.finders.scapetheads.services.AndroidRoom.LocalScore
import es.finders.scapetheads.services.AndroidRoom.ScoreSortType

data class LocalScoreState(
    val localScores: List<LocalScore> = emptyList(),
    val nickname: String = "",
    val date: String = "",
    val scoreVal: String = "",
    val time: String = "",
    val isAddingScore: Boolean = false,
    val scoreSortType: ScoreSortType = ScoreSortType.DATE
)