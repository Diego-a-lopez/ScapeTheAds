package es.finders.scapetheadds.menu.Defeat

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
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
import es.finders.scapetheadds.menu.login.NextButton
import es.finders.scapetheadds.ui.theme.RedPrimary
import es.finders.scapetheadds.ui.theme.ScapeTheAddsTheme
import es.finders.scapetheadds.ui.utils.BasicBackground

class Defeat : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            ScapeTheAddsTheme {
                DefeatScreen()
            }
        }
    }
}

@Composable
fun DefeatScreen(modifier: Modifier = Modifier) {
    Box(
        modifier,
        contentAlignment = Alignment.Center,
    ) {
        BasicBackground(modifier.fillMaxSize())
        DefeatLayout(modifier.fillMaxSize())
    }
}

@Preview(showBackground = false, showSystemUi = true)
@Composable
private fun DefeatScreenPreview() {
    ScapeTheAddsTheme {
        DefeatScreen()
    }
}

@Composable
fun DefeatLayout(modifier: Modifier = Modifier) {
    Column(
        modifier = modifier,
        verticalArrangement = Arrangement.Bottom,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        OutlineTextSection("Has perdido")
        Spacer(Modifier.size(300.dp))
        ScoreRow("200", Color.Black)
        Spacer(Modifier.size(16.dp))
        NextButton({},
            Modifier
                .padding(top = 5.dp, bottom = 20.dp)
                .fillMaxWidth(0.70f)
        )
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
fun ScoreRow(
    score: String,
    textColor: Color
) {
    // Variables for styling
    val paddingValue = 16.dp
    val scorePaddingValue = 8.dp

    Surface(
        shape = MaterialTheme.shapes.medium,
        border = BorderStroke(3.dp, Color.Black),
    ) {
        Row(
            modifier = Modifier.padding(paddingValue),
            verticalAlignment = Alignment.CenterVertically,
        ) {
            Text(text = "Puntuación:", color = textColor)
            Spacer(modifier = Modifier.weight(1f))
            Text(text = score, color = textColor)
            Spacer(modifier = Modifier.width(scorePaddingValue))
            Text(text = "pto", color = textColor)
        }
    }
}
