package es.finders.scapetheads.menu.home

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.ExitToApp
import androidx.compose.material.icons.outlined.Settings
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import es.finders.scapetheads.R
import es.finders.scapetheads.ui.theme.ScapeTheAddsTheme
import es.finders.scapetheads.ui.utils.IconButtonItem
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
        .fillMaxWidth(0.65f)
        .padding(vertical = 8.dp)
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        // PLAY
        IconButtonItem(
            stringResource(R.string.play),
            painterResource(id = R.drawable.joystick),
            onPlay,
            buttonModifier
        )

        // SELECT LEVEL
        IconButtonItem(
            stringResource(R.string.select_level),
            painterResource(id = R.drawable.grid_view),
            onSelectLevel,
            buttonModifier
        )

        // HIGHSCORES
        val localtext = stringResource(R.string.local_scores)
        IconButtonItem(
            localtext,
            painterResource(id = R.drawable.timeline),
            onHighscore,
            buttonModifier
        )

        // LEADERBOARD
        val globaltext = stringResource(R.string.global_scores)
        IconButtonItem(
            globaltext,
            painterResource(id = R.drawable.rank),
            onLeaderboard,
            buttonModifier
        )

        // SETTINGS
        IconButtonItem(
            stringResource(R.string.settings),
            Icons.Outlined.Settings,
            onSettings,
            buttonModifier
        )

        // EXIT
        IconButtonItem(
            stringResource(R.string.exit),
            Icons.Outlined.ExitToApp,
            onSignOut,
            buttonModifier
        )
    }
}

@Preview(showBackground = false, showSystemUi = true)
@Composable
fun HomePreview() {
    ScapeTheAddsTheme {
        HomeScreen({}, {}, {}, {}, {}, {})
    }
}
