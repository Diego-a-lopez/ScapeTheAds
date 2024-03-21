package es.finders.scapetheadds.menu.leaderboard

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
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
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.core.content.ContextCompat
import es.finders.scapetheadds.R
import es.finders.scapetheadds.menu.home.Home
import es.finders.scapetheadds.ui.theme.ScapeTheAddsTheme
import es.finders.scapetheadds.ui.utils.BackButton
import es.finders.scapetheadds.ui.utils.BasicBackground
import es.finders.scapetheadds.ui.utils.OutlineTextSection

class Leaderboard : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            val scoresType = intent.getStringExtra("scoresType") ?: stringResource(R.string.global_scores)
            //val scoresType = stringResource(R.string.global_scores)
            ScapeTheAddsTheme {
                LeaderboardScreen(scoresType)
            }
        }
    }
}

@Composable
fun LeaderboardScreen(scoresType: String, modifier: Modifier = Modifier) {
    val ctx = LocalContext.current
    Box(
        modifier,
        contentAlignment = Alignment.Center,
    ) {
        BasicBackground(modifier.fillMaxSize())
        LeaderboardLayout(ctx, scoresType, modifier.fillMaxSize())
    }
}

@Composable
fun LeaderboardLayout(ctx: Context, scoresType: String, modifier: Modifier = Modifier) {
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
        BackButton({
            Toast.makeText(ctx, "Going back to home screen", Toast.LENGTH_LONG).show()
            ContextCompat.startActivity(ctx, Intent(ctx, Home::class.java), null)
        })

        // Container for user info rows
        // Load local or global scores based on scoresType
        if (scoresType == stringResource(R.string.local_scores)) {
            OutlineTextSection(stringResource(R.string.local_scores))
            // Load local scores
            LocalScoresContainer(containerColor, containerOutlineColor)
        } else if (scoresType == stringResource(R.string.global_scores)) {
            OutlineTextSection(stringResource(R.string.global_scores))
            // Load global scores
            GlobalScoresContainer(containerColor, containerOutlineColor)
        }

    }
}

@Composable
fun LocalScoresContainer(containerColor: Color, containerOutlineColor: Color) {
    // Implement logic to load local scores
    UserInfoContainer(containerColor, containerOutlineColor)
}

@Composable
fun GlobalScoresContainer(containerColor: Color, containerOutlineColor: Color) {
    // Implement logic to load global scores
    UserInfoContainer(containerColor, containerOutlineColor)
}

@Composable
fun UserInfoContainer(containerColor: Color, containerOutlineColor: Color) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
    ) {
        // Each row wrapped in a Surface container
        UserInfoRow("User Name", "Score", containerOutlineColor)
        Spacer(modifier = Modifier.height(16.dp)) // Adding space between user rows
        UserInfoRow("John Doe", "100", containerOutlineColor)
        Spacer(modifier = Modifier.height(16.dp)) // Adding space between user rows
        // Add more users as needed
    }
}

@Composable
fun UserInfoRow(
    userName: String,
    score: String,
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
            Text(text = userName, color = textColor)
            Spacer(modifier = Modifier.weight(1f))
            Text(text = score, color = textColor)
            Spacer(modifier = Modifier.width(scorePaddingValue))
            Text(text = "pto", color = textColor)
        }
    }
}

@Preview(showBackground = false, showSystemUi = true)
@Composable
private fun LeaderboardScreenPreview() {
    ScapeTheAddsTheme {
        val scoresType = stringResource(R.string.local_scores)
        LeaderboardScreen(scoresType)
    }
}