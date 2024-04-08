package es.finders.scapetheadds.menu.home

import android.content.Context
import android.content.Intent
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
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
import es.finders.scapetheadds.R
import es.finders.scapetheadds.menu.leaderboard.Leaderboard
import es.finders.scapetheadds.menu.level.Level
import es.finders.scapetheadds.menu.levelselector.LevelSelector
import es.finders.scapetheadds.menu.settings.SettingsActivity
import es.finders.scapetheadds.ui.theme.ScapeTheAddsTheme
import es.finders.scapetheadds.ui.utils.BasicBackground
import es.finders.scapetheadds.ui.utils.ButtonItem
import es.finders.scapetheadds.ui.utils.Logo
import es.finders.scapetheadds.ui.utils.Title

class Home : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            ScapeTheAddsTheme {
                HomeScreen()
            }
        }
    }
}

@Composable
fun HomeScreen(modifier: Modifier = Modifier) {
    Box(
        modifier,
        contentAlignment = Alignment.Center,
    ) {
        BasicBackground(modifier.fillMaxSize())
        HomeLayout(modifier.fillMaxSize())
    }
}

@Composable
fun HomeLayout(modifier: Modifier = Modifier) {
    val ctx = LocalContext.current
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
        BottomButtonsSection(ctx)
    }
}

@Composable
fun BottomButtonsSection(ctx: Context) {
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
        ButtonItem(text = stringResource(R.string.select_level), {
            ContextCompat.startActivity(ctx, Intent(ctx, LevelSelector::class.java), null)
        }, buttonModifier)
        val localtext = stringResource(R.string.local_scores)
        ButtonItem(text = localtext, {
            ContextCompat.startActivity(
                ctx,
                Intent(ctx, Leaderboard::class.java).apply {
                    putExtra("scoresType", localtext)
                },
                null
            )
        }, buttonModifier)
        val globaltext = stringResource(R.string.global_scores)
        ButtonItem(text = globaltext, {
            ContextCompat.startActivity(
                ctx,
                Intent(ctx, Leaderboard::class.java).apply {
                    putExtra("scoresType", globaltext)
                },
                null
            )
        }, buttonModifier)
        ButtonItem(text = stringResource(R.string.settings), {
            ContextCompat.startActivity(ctx, Intent(ctx, SettingsActivity::class.java), null)
        }, buttonModifier)
    }
}

@Preview(showBackground = false, showSystemUi = true)
@Composable
fun HomePreview() {
    ScapeTheAddsTheme {
        HomeScreen()
    }
}
