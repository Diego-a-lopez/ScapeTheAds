package es.finders.scapetheads.menu.home

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.ExitToApp
import androidx.compose.material.icons.outlined.Settings
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.em
import es.finders.scapetheads.R
import es.finders.scapetheads.ui.theme.RedPrimary
import es.finders.scapetheads.ui.theme.ScapeTheAddsTheme
import es.finders.scapetheads.ui.utils.Logo
import es.finders.scapetheads.ui.utils.Title

@Composable
fun HomeScreen(
    onExit: () -> Unit,
    onPlay: () -> Unit,
    onSelectLevel: () -> Unit,
    onLeaderboard: () -> Unit,
    onHighscore: () -> Unit,
    onSettings: () -> Unit,
    modifier: Modifier = Modifier
) {
    Box(
        modifier,
        contentAlignment = Alignment.Center,
    ) {
        Column(
            modifier = modifier
                .fillMaxSize()
                .verticalScroll(rememberScrollState()),
            verticalArrangement = Arrangement.SpaceAround,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Title(
                Modifier
                    .fillMaxWidth()
            )
            Logo()
            BottomButtonsSection(
                onExit,
                onPlay,
                onSelectLevel,
                onLeaderboard,
                onHighscore,
                onSettings
            )
        }
    }
}

@Composable
fun BottomButtonsSection(
    onSignOut: () -> Unit,
    onPlay: () -> Unit,
    onSelectLevel: () -> Unit,
    onLeaderboard: () -> Unit,
    onHighscore: () -> Unit,
    onSettings: () -> Unit
) {
    val buttonModifier = Modifier
        .padding(vertical = 8.dp)
        .fillMaxWidth()
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp)
    ) {
        // PLAY
        Button(
            onClick = { onPlay() },
            modifier = buttonModifier,
            shape = RoundedCornerShape(35),
            contentPadding = PaddingValues(top = 10.dp, bottom = 10.dp),
            border = BorderStroke(1.dp, Color.Black),
            colors = ButtonDefaults.buttonColors(
                containerColor = RedPrimary
            )
        ) {
            Icon(
                painterResource(id = R.drawable.joystick),
                contentDescription = stringResource(R.string.play),
                tint = Color.White,
                modifier = Modifier.padding(end = 6.dp)
            )
            Text(
                text = stringResource(R.string.play),
                textAlign = TextAlign.Center,
                color = Color.White,
                fontSize = 5.em,
            )
        }

        // SELECT LEVEL
        Button(
            onClick = { onSelectLevel() },
            modifier = buttonModifier,
            shape = RoundedCornerShape(35),
            contentPadding = PaddingValues(top = 10.dp, bottom = 10.dp),
            border = BorderStroke(1.dp, Color.Black),
            colors = ButtonDefaults.buttonColors(
                containerColor = RedPrimary
            )
        ) {
            Icon(
                painterResource(id = R.drawable.grid_view),
                contentDescription = stringResource(R.string.select_level),
                tint = Color.White,
                modifier = Modifier.padding(end = 6.dp)
            )
            Text(
                text = stringResource(R.string.select_level),
                textAlign = TextAlign.Center,
                color = Color.White,
                fontSize = 5.em,
            )
        }


        // HIGHSCORES
        val localtext = stringResource(R.string.local_scores)
        Button(
            onClick = { onHighscore() },
            modifier = buttonModifier,
            shape = RoundedCornerShape(35),
            contentPadding = PaddingValues(top = 10.dp, bottom = 10.dp),
            border = BorderStroke(1.dp, Color.Black),
            colors = ButtonDefaults.buttonColors(
                containerColor = RedPrimary
            )
        ) {
            Icon(
                painterResource(id = R.drawable.timeline),
                contentDescription = localtext,
                tint = Color.White,
                modifier = Modifier.padding(end = 6.dp)
            )
            Text(
                text = localtext,
                textAlign = TextAlign.Center,
                color = Color.White,
                fontSize = 5.em,
            )
        }

        // LEADERBOARD
        val globaltext = stringResource(R.string.global_scores)
        Button(
            onClick = { onLeaderboard() },
            modifier = buttonModifier,
            shape = RoundedCornerShape(35),
            contentPadding = PaddingValues(top = 10.dp, bottom = 10.dp),
            border = BorderStroke(1.dp, Color.Black),
            colors = ButtonDefaults.buttonColors(
                containerColor = RedPrimary
            )
        ) {
            Icon(
                painterResource(id = R.drawable.rank),
                contentDescription = globaltext,
                tint = Color.White,
                modifier = Modifier.padding(end = 6.dp)
            )
            Text(
                text = globaltext,
                textAlign = TextAlign.Center,
                color = Color.White,
                fontSize = 5.em,
            )
        }

        // SETTINGS
        Button(
            onClick = { onSettings() },
            modifier = buttonModifier,
            shape = RoundedCornerShape(35),
            contentPadding = PaddingValues(top = 10.dp, bottom = 10.dp),
            border = BorderStroke(1.dp, Color.Black),
            colors = ButtonDefaults.buttonColors(
                containerColor = RedPrimary
            )
        ) {
            Icon(
                Icons.Outlined.Settings,
                contentDescription = stringResource(R.string.settings),
                tint = Color.White,
                modifier = Modifier.padding(end = 6.dp)
            )
            Text(
                text = stringResource(R.string.settings),
                textAlign = TextAlign.Center,
                color = Color.White,
                fontSize = 5.em,
            )
        }

        // EXIT
        Button(
            onClick = { onSignOut() },
            modifier = buttonModifier,
            shape = RoundedCornerShape(35),
            contentPadding = PaddingValues(top = 10.dp, bottom = 10.dp),
            border = BorderStroke(1.dp, Color.Black),
            colors = ButtonDefaults.buttonColors(
                containerColor = RedPrimary
            )
        ) {
            Icon(
                Icons.Outlined.ExitToApp,
                contentDescription = stringResource(id = R.string.exit),
                tint = Color.White,
                modifier = Modifier.padding(end = 6.dp)
            )
            Text(
                text = stringResource(R.string.exit),
                textAlign = TextAlign.Center,
                color = Color.White,
                fontSize = 5.em,
            )
        }
    }
}

@Preview(showBackground = false, showSystemUi = true)
@Composable
fun HomePreview() {
    ScapeTheAddsTheme {
        HomeScreen({}, {}, {}, {}, {}, {})
    }
}
