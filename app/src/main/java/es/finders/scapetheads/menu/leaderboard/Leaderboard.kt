package es.finders.scapetheads.menu.leaderboard

import android.widget.Toast
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import es.finders.scapetheadds.AndroidRoom.LocalScoreState
import es.finders.scapetheads.R
import es.finders.scapetheads.services.firestore.FirestoreClient
import es.finders.scapetheads.services.APIService.ApiClient
import es.finders.scapetheads.services.APIService.HighScore
import es.finders.scapetheads.ui.theme.ScapeTheAddsTheme
import es.finders.scapetheads.ui.utils.BackButton
import es.finders.scapetheads.ui.utils.BasicBackground
import es.finders.scapetheads.ui.utils.OutlineTextSection

@Composable
fun LeaderboardScreen(
    onExit: () -> Unit,
    scoresType: String,
    modifier: Modifier = Modifier,
    state: LocalScoreState,
    firestoreClient: FirestoreClient
) {
    val ctx = LocalContext.current
    //val call = ApiClient.apiService.getScoreByUserName("John")//ApiClient.apiService.getHighScores(10)
    val call = ApiClient.apiService.getAllHighScores()
    val highScores = remember { mutableStateListOf<HighScore>() }


    if (scoresType == stringResource(R.string.global_scores)) {

        try {
            firestoreClient.getTopHighscores(10)
        } finally {
            highScores.clear()
            highScores.add(HighScore("Failed to retrieve scores", "0", "0", "0"))
        }

    } else if (scoresType == stringResource(R.string.local_scores)) {
        highScores.clear()
        state.localScores.forEach { localScore ->
            val highScore = HighScore(

                user = localScore.nickname, // Assuming user is a property of your local score
                date = localScore.date,
                score = localScore.score, // Assuming score is a property of your local score
                time = localScore.time // Assuming time is a property of your local score
            )
            highScores.add(highScore)
        }

    } else {
        highScores.clear()
        highScores.add(HighScore("Failed to retrieve scores", "0", "0", "0"))
        Toast.makeText(
            ctx,
            "Failed to retrieve scores: Score type undefined",
            Toast.LENGTH_LONG
        ).show()
    }

    Box(
        modifier,
        contentAlignment = Alignment.Center,
    ) {
        BasicBackground(modifier.fillMaxSize())
        LeaderboardLayout(onExit, scoresType, modifier.fillMaxSize(), highScores)
    }
}

@Composable
fun LeaderboardLayout(
    onExit: () -> Unit,
    scoresType: String,
    modifier: Modifier = Modifier,
    highScores: List<HighScore>,
) {
    // Variables for styling
    val containerColor = MaterialTheme.colorScheme.surface
    val containerOutlineColor = MaterialTheme.colorScheme.onSurface

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        // Upper Left Arrow to go back to home screen
        BackButton(onExit)

        // Container for user info rows
        // Load local or global scores based on scoresType
        if (scoresType == stringResource(R.string.local_scores)) {
            OutlineTextSection(stringResource(R.string.local_scores))
            // Load local scores
            LocalScoresContainer(containerColor, containerOutlineColor, highScores)
        } else if (scoresType == stringResource(R.string.global_scores)) {
            OutlineTextSection(stringResource(R.string.global_scores))
            // Load global scores
            GlobalScoresContainer(containerColor, containerOutlineColor, highScores)
        }

    }
}

@Composable
fun LocalScoresContainer(
    containerColor: Color,
    containerOutlineColor: Color,
    localScores: List<HighScore>
) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
    ) {
        // Each row wrapped in a Surface container
        UserInfoRow(
            stringResource(R.string.user_name),
            stringResource(R.string.date),
            stringResource(R.string.score),
            stringResource(R.string.time),
            containerOutlineColor
        )
        Spacer(modifier = Modifier.height(16.dp)) // Adding space between user rows
        Column(
            modifier = Modifier
                .verticalScroll(rememberScrollState())
        ) {
            localScores.forEach { score ->
                UserInfoRow(score.user, score.date, score.score, score.time, containerOutlineColor)
                Spacer(modifier = Modifier.height(16.dp)) // Adding space between user rows
            }
        }
    }
}


@Composable
fun GlobalScoresContainer(
    containerColor: Color,
    containerOutlineColor: Color,
    highScores: List<HighScore>
) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
    ) {


        // Each row wrapped in a Surface container
        UserInfoRow(
            stringResource(R.string.user_name),
            stringResource(R.string.date),
            stringResource(R.string.score),
            stringResource(R.string.time),
            containerOutlineColor
        )
        Spacer(modifier = Modifier.height(16.dp)) // Adding space between user rows
        Column(
            modifier = Modifier
                .verticalScroll(rememberScrollState())
        ) {

            highScores.forEach { score ->
                //UserInfoRow("Global John Doe", "100", "100", containerOutlineColor)
                UserInfoRow(score.user, score.date, score.score, score.time, containerOutlineColor)
                Spacer(modifier = Modifier.height(16.dp)) // Adding space between user rows
            }
        }
        // Add more users as needed
    }

}

@Composable
fun UserInfoRow(
    user: String,
    date: String,
    score: String,
    time: String,
    textColor: Color
) {
    // Variables for styling
    val paddingValue = 16.dp
    val scorePaddingValue = 8.dp

    Surface(
        modifier = Modifier.fillMaxWidth(),
        shape = MaterialTheme.shapes.medium,
        border = BorderStroke(3.dp, Color.Black),
    ) {
        Row(
            modifier = Modifier.padding(paddingValue),
            verticalAlignment = Alignment.CenterVertically,
        ) {
            Text(text = user, color = textColor)
            Spacer(modifier = Modifier.weight(1f))
            Text(text = date, color = textColor)
            Spacer(modifier = Modifier.weight(1f))
            Text(text = score, color = textColor)
            Spacer(modifier = Modifier.width(scorePaddingValue))
            Text(text = stringResource(R.string.points), color = textColor)
            Spacer(modifier = Modifier.width(scorePaddingValue))
            Text(text = time, color = textColor)
            Spacer(modifier = Modifier.width(scorePaddingValue))
            Text(text = stringResource(R.string.seconds), color = textColor)

        }
    }
}

@Preview(showBackground = false, showSystemUi = true)
@Composable
private fun LeaderboardScreenPreview() {
    ScapeTheAddsTheme {
        val scoresType = stringResource(R.string.local_scores)
        //LeaderboardScreen(scoresType)
    }
}