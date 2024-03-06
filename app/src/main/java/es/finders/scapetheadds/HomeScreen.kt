import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Text
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import es.finders.scapetheadds.ui.theme.ScapeTheAddsTheme
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.text.style.TextAlign

class HomeScreen() : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            ScapeTheAddsTheme {
                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    HomeMenu()
                }
            }
        }
    }
}
@Composable
fun HomeMenu(modifier: Modifier = Modifier) {
    val buttonHeight = 100.dp // Adjust button height as needed
    val buttonWidth = buttonHeight * 5 // Width is 5 times the height
    val buttonModifier = Modifier
        .padding(16.dp)
        .height(buttonHeight)
        .width(buttonWidth)

    val buttonColors = ButtonDefaults.buttonColors(
        contentColor = MaterialTheme.colorScheme.onPrimary
    )

    val buttonShape = MaterialTheme.shapes.medium

    Column(
        modifier = modifier
            .fillMaxSize()
    ) {
        // Text on the upper half
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .weight(1f),
            contentAlignment = Alignment.Center
        ) {
            Text(
                text = "Scape The Adds",
                modifier = Modifier.padding(horizontal = 16.dp),
                textAlign = TextAlign.Center,
                style = MaterialTheme.typography.displaySmall
            )
        }

        // Placeholder app icon
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .weight(1f),
            contentAlignment = Alignment.Center
        ) {
            // Placeholder app icon goes here
            Text(text = "App Icon Placeholder")
        }

        // Bottom half with buttons
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 16.dp)
        ) {
            Button(
                onClick = { /* Handle button click */ },
                modifier = buttonModifier,
                shape = buttonShape,
                colors = buttonColors
            ) {
                Text(text = "Jugar")
            }
            Button(
                onClick = { /* Handle button click */ },
                modifier = buttonModifier,
                shape = buttonShape,
                colors = buttonColors
            ) {
                Text(text = "Selección de Niveles")
            }
            Button(
                onClick = { /* Handle button click */ },
                modifier = buttonModifier,
                shape = buttonShape,
                colors = buttonColors
            ) {
                Text(text = "Puntuación Local")
            }
            Button(
                onClick = { /* Handle button click */ },
                modifier = buttonModifier,
                shape = buttonShape,
                colors = buttonColors
            ) {
                Text(text = "Puntuación Global")
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun HomePreview() {
    ScapeTheAddsTheme {
        HomeMenu()
    }
}