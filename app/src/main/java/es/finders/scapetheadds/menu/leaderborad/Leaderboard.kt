package es.finders.scapetheadds.menu.leaderborad

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import es.finders.scapetheadds.ui.theme.ScapeTheAddsTheme

class Leaderboard : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            ScapeTheAddsTheme {
                LeaderboardScreen()
            }
        }
    }
}

@Composable
fun LeaderboardScreen() {
    // Variables for styling
    val textTitle = "Puntuación Global"
    val titleTextColor = MaterialTheme.colorScheme.onPrimary
    val titleTextOutlineColor = MaterialTheme.colorScheme.onTertiary

    val containerColor = MaterialTheme.colorScheme.onSecondary
    val containerOutlineColor = MaterialTheme.colorScheme.onTertiary
    val containerElevation = 4.dp

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        // Upper Left Arrow to go back to home screen
        Icon(
            imageVector = Icons.Default.ArrowBack,
            contentDescription = "Back",
            modifier = Modifier
                .size(48.dp)
                .clickable {
                    // Handle navigation back to home screen
                }
        )

        // Top Text Field "Puntuación Global"
        Text(
            text = textTitle,
            style = MaterialTheme.typography.displaySmall
                .copy(color = titleTextColor),
            modifier = Modifier.align(Alignment.CenterHorizontally)
        )

        // Container for user info rows
        Column(
            modifier = Modifier
                .fillMaxWidth()
        ) {
            // Each row wrapped in a Surface container
            Surface(
                modifier = Modifier.fillMaxWidth(),
                color = containerColor,
                shape = MaterialTheme.shapes.medium,
                border = BorderStroke(1.dp, containerOutlineColor),
            ) {
                UserInfoRow("User Name", "Score", textColor = containerOutlineColor)
            }
            Surface(
                modifier = Modifier.fillMaxWidth(),
                color = containerColor,
                shape = MaterialTheme.shapes.medium,
                border = BorderStroke(1.dp, containerOutlineColor),
            ) {
                UserInfoRow("John Doe", "100", textColor = containerOutlineColor)
            }
            // Add more Surface containers for other users as needed
        }
    }
}


@Composable
fun UserInfoRow(
    userName: String,
    score: String,
    textColor: androidx.compose.ui.graphics.Color
) {
    // Variables for styling
    val paddingValue = 16.dp
    val scorePaddingValue = 8.dp

    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(paddingValue),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Text(text = userName, color = textColor)
        Spacer(modifier = Modifier.weight(1f))
        Text(text = score, color = textColor)
        Spacer(modifier = Modifier.width(scorePaddingValue))
        Text(text = "pto", color = textColor)
    }
}

@Preview(showBackground = false, showSystemUi = true)
@Composable
private fun LeaderboardScreenPreview() {
    ScapeTheAddsTheme {
        LeaderboardScreen()
    }
}