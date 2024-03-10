package es.finders.scapetheadds.menu.home

import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.LocalTextStyle
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.StrokeJoin
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.core.content.ContextCompat
import es.finders.scapetheadds.menu.level.Level
import es.finders.scapetheadds.ui.theme.RedPrimary
import es.finders.scapetheadds.ui.theme.ScapeTheAddsTheme
import es.finders.scapetheadds.ui.utils.BasicBackground
import es.finders.scapetheadds.ui.utils.Logo

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
        OutlineTextSection("Scape The Adds")
        Logo()
        BottomButtonsSection()
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
                    fontSize = MaterialTheme.typography.displayMedium.fontSize,
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
                    fontSize = MaterialTheme.typography.displayMedium.fontSize,
                    drawStyle = Stroke(width = 5f, join = StrokeJoin.Round)
                )
            )
        )
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
        ButtonItem(text = "Jugar", {
            Toast.makeText(ctx, "Playing game", Toast.LENGTH_LONG).show()
            ContextCompat.startActivity(ctx, Intent(ctx, Level::class.java), null)
        }, buttonModifier)
        ButtonItem(text = "Selección de Niveles", {}, buttonModifier)
        ButtonItem(text = "Puntuación Local", {}, buttonModifier)
        ButtonItem(text = "Puntuación Global", {}, buttonModifier)
    }
}

@Composable
fun ButtonItem(text: String, onClick: () -> Unit, modifier: Modifier) {


    Button(
        onClick = { onClick() },
        modifier = modifier,
        shape = RoundedCornerShape(35),
        contentPadding = PaddingValues(top = 10.dp, bottom = 10.dp),
        border = BorderStroke(1.dp, Color.Black),
        colors = ButtonDefaults.buttonColors(
            containerColor = RedPrimary
        )
    ) {
        Text(text = text)
    }
}

@Preview(showBackground = true)
@Composable
fun HomePreview() {
    ScapeTheAddsTheme {
        HomeScreen()
    }
}
