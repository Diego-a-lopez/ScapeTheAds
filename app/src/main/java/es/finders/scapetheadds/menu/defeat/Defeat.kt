package es.finders.scapetheadds.menu.Defeat

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.em
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
        DefeatText()
        Spacer(Modifier.size(350.dp))
        Surface(color = Color.White,
            shape = RoundedCornerShape(35),
            border = BorderStroke(1.dp, Color.Black)
        ){
            Text(
                text= "Puntuación:        200 puntos",
                Modifier
                    .padding(start = 5.dp, top = 5.dp, bottom = 5.dp)
                    .fillMaxWidth(0.70f)
            )
        }
        Spacer(Modifier.size(16.dp))
        NextButton({},
            Modifier
                .padding(top = 5.dp, bottom = 20.dp)
                .fillMaxWidth(0.70f)
        )
    }
}
@Composable
fun DefeatText(modifier: Modifier = Modifier) {
    Text(
        text = "Has perdido",
        textAlign = TextAlign.Center,
        color = RedPrimary,
        fontSize = 10.em,
    )
}

