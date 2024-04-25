package es.finders.scapetheads.menu.home

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import es.finders.scapetheads.R
import es.finders.scapetheads.ui.theme.ScapeTheAddsTheme
import es.finders.scapetheads.ui.utils.ButtonItem
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
        ButtonItem(text = stringResource(R.string.play), onPlay, buttonModifier)
        ButtonItem(text = stringResource(R.string.select_level), onSelectLevel, buttonModifier)
        val localtext = stringResource(R.string.local_scores)
        ButtonItem(text = localtext, onHighscore, buttonModifier)
        val globaltext = stringResource(R.string.global_scores)
        ButtonItem(text = globaltext, onLeaderboard, buttonModifier)
        ButtonItem(text = stringResource(R.string.settings), onSettings, buttonModifier)
        ButtonItem(text = stringResource(R.string.exit), onSignOut, buttonModifier)
    }
}

@Preview(showBackground = false, showSystemUi = true)
@Composable
fun HomePreview() {
    ScapeTheAddsTheme {
        HomeScreen({}, {}, {}, {}, {}, {})
    }
}
