package es.finders.scapetheadds.menu.levelselector

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.em
import es.finders.scapetheadds.menu.login.BasicInput
import es.finders.scapetheadds.menu.login.NextButton
import es.finders.scapetheadds.menu.login.PasswordInput
import es.finders.scapetheadds.ui.theme.ScapeTheAddsTheme
import es.finders.scapetheadds.ui.utils.BasicBackground

class LevelSelector : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            ScapeTheAddsTheme {
                LevelSelectorScreen()
            }
        }
    }
}

@Composable
fun LevelSelectorScreen(modifier: Modifier = Modifier) {
    Box(
        modifier,
        contentAlignment = Alignment.Center,
    ) {
        BasicBackground(modifier.fillMaxSize())
        LevelSelectorLayout(modifier.fillMaxSize())
    }
}

@Preview(showBackground = false, showSystemUi = true)
@Composable
private fun LevelSelectorScreenPreview() {
    ScapeTheAddsTheme {
        LevelSelectorScreen()
    }
}

@Composable
fun LevelSelectorLayout(modifier: Modifier = Modifier) {
    Column(
        modifier = modifier,
        verticalArrangement = Arrangement.Bottom,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        LooseText()
        Spacer(Modifier.size(100.dp))
        BasicInput()
        Spacer(Modifier.size(16.dp))
        PasswordInput()
        Spacer(Modifier.size(16.dp))
        NextButton({},
            Modifier
                .padding(top = 5.dp, bottom = 20.dp)
                .fillMaxWidth(0.70f)
        )
    }
}
@Composable
fun LooseText(modifier: Modifier = Modifier) {
    Text(
        text = "Has perdido",
        textAlign = TextAlign.Center,
        color = Color.White,
        fontSize = 5.em,
    )
}

