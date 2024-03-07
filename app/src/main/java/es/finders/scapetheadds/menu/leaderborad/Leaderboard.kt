package es.finders.scapetheadds.menu.leaderboard

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.Icon
import androidx.compose.material3.LocalTextStyle
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.StrokeJoin
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import es.finders.scapetheadds.menu.login.LoginLayout
import es.finders.scapetheadds.ui.theme.RedPrimary
import es.finders.scapetheadds.ui.theme.ScapeTheAddsTheme
import es.finders.scapetheadds.ui.utils.BasicBackground

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
fun LeaderboardScreen(modifier: Modifier = Modifier) {
    Box(
        modifier,
        contentAlignment = Alignment.Center,
    ) {
        BasicBackground(modifier.fillMaxSize())
        LeaderboardLayout(modifier.fillMaxSize())
    }
}
@Composable
fun LeaderboardLayout(modifier: Modifier = Modifier) {
    // Variables for styling
    val textTitle = "Puntuación Global"
    val titleTextColor = MaterialTheme.colorScheme.onPrimary
    val titleTextOutlineColor = MaterialTheme.colorScheme.onSecondary

    val containerColor = MaterialTheme.colorScheme.surface
    val containerOutlineColor = MaterialTheme.colorScheme.onSurface
    val containerElevation = 4.dp

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        // Upper Left Arrow to go back to home screen
        BackButton()

        OutlineTextSection("Puntuación Global")

        // Container for user info rows
        UserInfoContainer(containerColor, containerOutlineColor)
    }
}
@Composable
fun OutlineTextSection(text: String) {
    Box(
        modifier = Modifier.fillMaxWidth(),
        contentAlignment = Alignment.Center
    ) {
        Text(
            text = text,
            modifier = Modifier.padding(horizontal = 16.dp),
            textAlign = TextAlign.Center,
            style = LocalTextStyle.current.merge(
                TextStyle(
                    color = RedPrimary, // Set to primary color
                    fontSize = MaterialTheme.typography.displaySmall.fontSize,
                )
            ),
        )
        Text(
            text = text,
            modifier = Modifier.padding(horizontal = 16.dp),
            textAlign = TextAlign.Center,
            style = LocalTextStyle.current.merge(
                TextStyle(
                    color = Color.Black, // Set to primary color
                    fontSize = MaterialTheme.typography.displaySmall.fontSize,
                    drawStyle = Stroke(width = 4f, join = StrokeJoin.Round)
                )
            )
        )
    }
}
@Composable
fun BackButton() {
    Icon(
        imageVector = Icons.Default.ArrowBack,
        contentDescription = "Back",
        modifier = Modifier
            .size(48.dp)
            .clickable {
                // Handle navigation back to home screen
            }
    )
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

@Preview(showBackground = true)
@Composable
private fun LeaderboardScreenPreview() {
    ScapeTheAddsTheme {
        LeaderboardScreen()
    }
}