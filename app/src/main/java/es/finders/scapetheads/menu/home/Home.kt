package es.finders.scapetheads.menu.home

import android.content.Context
import android.content.Intent
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.core.content.ContextCompat
import es.finders.scapetheads.R
import es.finders.scapetheads.menu.level.Level
import es.finders.scapetheads.ui.theme.ScapeTheAddsTheme
import es.finders.scapetheads.ui.utils.ButtonItem
import es.finders.scapetheads.ui.utils.Logo
import es.finders.scapetheads.ui.utils.Title

@Composable
fun HomeScreen(
    onExit: () -> Unit,
    onSelectLevel: () -> Unit,
    onLeaderboard: () -> Unit,
    onHighscore: () -> Unit,
    onSettings: () -> Unit,
    modifier: Modifier = Modifier
) {
    val ctx = LocalContext.current
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
                    .width(100.dp)
            )
            Logo()
            BottomButtonsSection(onSelectLevel, onLeaderboard, onHighscore, onSettings, ctx)
        }
    }
}

@Composable
fun BottomButtonsSection(
    onSelectLevel: () -> Unit,
    onLeaderboard: () -> Unit,
    onHighscore: () -> Unit,
    onSettings: () -> Unit,
    ctx: Context
) {
    val buttonModifier = Modifier
        .padding(vertical = 8.dp)
        .fillMaxWidth()
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp)
    ) {
        ButtonItem(text = stringResource(R.string.play), {
            ContextCompat.startActivity(
                ctx,
                Intent(ctx, Level::class.java),
                null
            )
        }, buttonModifier)
        ButtonItem(text = stringResource(R.string.select_level), onSelectLevel, buttonModifier)
        val localtext = stringResource(R.string.local_scores)
        ButtonItem(text = localtext, onHighscore, buttonModifier)
        val globaltext = stringResource(R.string.global_scores)
        ButtonItem(text = globaltext, onLeaderboard, buttonModifier)
        ButtonItem(text = stringResource(R.string.settings), onSettings, buttonModifier)
    }
}

@Preview(showBackground = false, showSystemUi = true)
@Composable
fun HomePreview() {
    ScapeTheAddsTheme {
        HomeScreen({}, {}, {}, {}, {})
    }
}
