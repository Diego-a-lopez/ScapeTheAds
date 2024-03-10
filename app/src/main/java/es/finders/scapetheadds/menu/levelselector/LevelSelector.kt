package es.finders.scapetheadds.menu.levelselector

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
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
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import es.finders.scapetheadds.R
import es.finders.scapetheadds.ui.theme.RedPrimary
import es.finders.scapetheadds.ui.theme.ScapeTheAddsTheme
import es.finders.scapetheadds.ui.utils.BasicBackground
import es.finders.scapetheadds.ui.utils.OutlineTextSection

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
fun LevelButton(i: Int, blocked: Boolean) {
    Surface(
        modifier = Modifier
            .width(70.dp)
            .padding(4.dp)
            .height(70.dp),
        shape = MaterialTheme.shapes.small,
        color = if (blocked) RedPrimary else Color.White,
        border = BorderStroke(1.dp, color = Color.Black)
    ) {
        Text(
            textAlign = TextAlign.Center,
            text = i.toString(),
            color = if (blocked) Color.White else RedPrimary
        )
    }
}

@Composable
fun LevelSelectorLayout(modifier: Modifier = Modifier) {
    Column(
        modifier = Modifier
            .padding(4.dp)
            .fillMaxWidth()
            .fillMaxHeight(),
        verticalArrangement = Arrangement.SpaceEvenly,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        OutlineTextSection(stringResource(R.string.level_selection))
        Row {
            LevelButton(1, blocked = false)
            LevelButton(2, blocked = false)
            LevelButton(3, blocked = false)
            LevelButton(4, blocked = false)
        }
        Row {
            LevelButton(5, blocked = false)
            LevelButton(6, blocked = false)
            LevelButton(7, blocked = false)
            LevelButton(8, blocked = false)
        }
        Row {
            LevelButton(9, blocked = true)
            LevelButton(10, blocked = true)
            LevelButton(11, blocked = true)
            LevelButton(12, blocked = false)
        }
        Row {
            LevelButton(13, blocked = false)
            LevelButton(14, blocked = false)
            LevelButton(15, blocked = false)
            LevelButton(16, blocked = false)
        }
        Row {
            LevelButton(17, blocked = false)
            LevelButton(18, blocked = true)
            LevelButton(19, blocked = false)
            LevelButton(20, blocked = false)
        }
        Row {
            LevelButton(21, blocked = false)
            LevelButton(22, blocked = false)
            LevelButton(23, blocked = true)
        }
    }
}
