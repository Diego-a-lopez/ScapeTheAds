package es.finders.scapetheadds.menu.home

import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.core.content.ContextCompat
import es.finders.scapetheadds.R
import es.finders.scapetheadds.menu.level.Level
import es.finders.scapetheadds.ui.theme.ScapeTheAddsTheme
import es.finders.scapetheadds.ui.utils.BasicBackground
import es.finders.scapetheadds.ui.utils.ButtonItem
import es.finders.scapetheadds.ui.utils.Logo
import es.finders.scapetheadds.ui.utils.OutlineTextSection

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
    Column(
        modifier = modifier.fillMaxSize(),
        verticalArrangement = Arrangement.SpaceAround,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        OutlineTextSection(
            "Scape The Adds",
            textSize = MaterialTheme.typography.displayMedium.fontSize
        )
        Logo()
        BottomButtonsSection()
    }
}

@Composable
fun BottomButtonsSection() {
    val ctx = LocalContext.current
    val buttonModifier = Modifier
        .padding(vertical = 8.dp)
        .fillMaxWidth()
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp)
    ) {
        ButtonItem(text = stringResource(R.string.play), {
            Toast.makeText(ctx, "Playing game", Toast.LENGTH_LONG).show()
            ContextCompat.startActivity(ctx, Intent(ctx, Level::class.java), null)
        }, buttonModifier)
        ButtonItem(text = stringResource(R.string.select_level), {}, buttonModifier)
        ButtonItem(text = stringResource(R.string.local_scores), {}, buttonModifier)
        ButtonItem(text = stringResource(R.string.global_scores), {}, buttonModifier)
    }
}

@Preview(showBackground = false, showSystemUi = true)
@Composable
fun HomePreview() {
    ScapeTheAddsTheme {
        HomeScreen()
    }
}
