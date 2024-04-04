package es.finders.scapetheadds.menu.settings

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Slider
import androidx.compose.material3.Switch
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.core.content.ContextCompat
import es.finders.scapetheadds.R
import es.finders.scapetheadds.menu.home.Home
import es.finders.scapetheadds.ui.theme.ScapeTheAddsTheme
import es.finders.scapetheadds.ui.utils.BackButton
import es.finders.scapetheadds.ui.utils.BasicBackground
import es.finders.scapetheadds.ui.utils.ButtonItem
import es.finders.scapetheadds.ui.utils.Logo
import es.finders.scapetheadds.ui.utils.OutlineTextSection

class SettingsActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            ScapeTheAddsTheme {
                SettingsScreen()
            }
        }
    }
}

@Composable
fun SettingsScreen(modifier: Modifier = Modifier) {
    Box(
        modifier,
        contentAlignment = Alignment.Center,
    ) {
        BasicBackground(modifier.fillMaxSize())
        SettingsScreenLayout(modifier.fillMaxSize())
    }
}

@Composable
fun SettingsScreenLayout(modifier: Modifier = Modifier) {
    val ctx = LocalContext.current
    Column(
        modifier = modifier
            .fillMaxSize(),
        verticalArrangement = Arrangement.SpaceAround,

        ) {
        BackButton({
            ContextCompat.startActivity(ctx, Intent(ctx, Home::class.java), null)
        })
        OutlineTextSection(
            stringResource(R.string.settings),
            textSize = MaterialTheme.typography.displayMedium.fontSize
        )
        Spacer(modifier = Modifier.height(16.dp))
        Logo(modifier = Modifier.align(Alignment.CenterHorizontally))
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
        LabeledSetting(stringResource(R.string.language))
        LanguageSettings(ctx)
        Spacer(modifier = Modifier.height(16.dp))
        LabeledSetting(stringResource(R.string.volume))
        VolumeSettings(ctx)
        Spacer(modifier = Modifier.height(16.dp))
        LabeledSetting(stringResource(R.string.theme))
        Spacer(modifier = Modifier.width(16.dp))
        ThemeSettings(ctx)
    }
}

@Composable
fun LabeledSetting(label: String) {
    OutlineTextSection(
        label,
        contentAlignment = Alignment.CenterStart,
        textSize = MaterialTheme.typography.displayMedium.fontSize
    )
}

@Composable
fun LanguageSettings(ctx: Context) {
    val buttonModifier = Modifier
        .padding(vertical = 8.dp)
        .fillMaxWidth()
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp)
            .padding(vertical = 8.dp),
    ) {
        ButtonItem("English", {
            Toast.makeText(ctx, "Changed language to English", Toast.LENGTH_LONG).show()
        }, buttonModifier)
        ButtonItem("Spanish", {
            Toast.makeText(ctx, "Changed language to Spanish", Toast.LENGTH_LONG).show()
        }, buttonModifier)
    }
}

/*
@Composable
fun LanguageSettings(ctx: Context) {
    val languages = listOf("English", "Spanish")
    var selectedLanguage by remember { mutableStateOf(0) }

    Column(modifier = Modifier.fillMaxSize()) {
        Text("Select Language:")
        DropdownMenu(
            modifier = Modifier.padding(16.dp),
            expanded = false,
            onDismissRequest = { /* Dismiss */ }
        ) {
            languages.forEachIndexed { index, language ->
                DropdownMenuItem(onClick = {
                    selectedLanguage = index
                    Toast.makeText(ctx, "Language set to: $language", Toast.LENGTH_SHORT).show()
                }) {
                    Text(text = language)
                }
            }
        }
    }
}
*/
@Composable
fun VolumeSettings(ctx: Context) {
    var sliderPosition: Float = 0f
    Column {
        Slider(
            value = sliderPosition,
            onValueChange = {
                sliderPosition = it
                Toast.makeText(ctx, "Volume changed", Toast.LENGTH_LONG).show()
            }
        )
        Text(text = sliderPosition.toString())
    }

}

@Composable
fun ThemeSettings(ctx: Context) {
    val isDarkTheme = isSystemInDarkTheme()
    val switchLabel = if (isDarkTheme) "Dark Theme" else "Light Theme"

    val toggleTheme: () -> Unit = {
        Toast.makeText(
            ctx,
            "Theme changed to ${if (isDarkTheme) "Light" else "Dark"}",
            Toast.LENGTH_SHORT
        ).show()
        // Toggle theme logic goes here
    }

    Switch(
        checked = isDarkTheme,
        onCheckedChange = { toggleTheme.invoke() },
    )
}

@Preview(showBackground = true)
@Composable
fun SettingsPreview() {
    ScapeTheAddsTheme {
        SettingsScreen()
    }
}