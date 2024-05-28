package es.finders.scapetheads.menu.settings

import android.content.Context
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Slider
import androidx.compose.material3.Switch
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import es.finders.scapetheads.R
import es.finders.scapetheads.ui.theme.ScapeTheAddsTheme
import es.finders.scapetheads.ui.utils.BackButton
import es.finders.scapetheads.ui.utils.ButtonItem
import es.finders.scapetheads.ui.utils.CardBackgroundColumn
import es.finders.scapetheads.ui.utils.Logo
import es.finders.scapetheads.ui.utils.OutlineTextSection
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow

@Composable
fun SettingsScreen(
    modifier: Modifier = Modifier,
    onExit: () -> Unit,
    onSpanish: () -> Unit,
    onEnglish: () -> Unit,
    preferencesLanguageFlow: Flow<String>,
    onVolume: (newVolumeValue: Int) -> Unit,
    preferencesVolumeFlow: Flow<Int>,
    onTheme: (isDark: Boolean) -> Unit,
    preferencesThemeFlow: Flow<Boolean>
) {
    Box(
        modifier,
        contentAlignment = Alignment.Center,
    ) {
        SettingsScreenLayout(
            modifier.fillMaxSize(),
            onSpanish,
            onEnglish,
            preferencesLanguageFlow,
            onVolume,
            preferencesVolumeFlow,
            onTheme,
            preferencesThemeFlow
        )

        BackButton(
            onExit, Modifier
                .align(Alignment.TopEnd)
                .padding(top = 20.dp)
        )
    }
}

@Composable
fun SettingsScreenLayout(
    modifier: Modifier = Modifier,
    onSpanish: () -> Unit,
    onEnglish: () -> Unit,
    preferencesLanguageFlow: Flow<String>,
    onVolume: (newVolumeValue: Int) -> Unit,
    preferencesVolumeFlow: Flow<Int>,
    onTheme: (isDark: Boolean) -> Unit,
    preferencesThemeFlow: Flow<Boolean>
) {
    val ctx = LocalContext.current

    CardBackgroundColumn(
        horizontalAlignment = Alignment.Start,
        verticalArrangement = Arrangement.SpaceAround,
    ) {
        BottomButtonsSection(
            ctx,
            onSpanish,
            onEnglish,
            preferencesLanguageFlow,
            onVolume,
            preferencesVolumeFlow,
            onTheme,
            preferencesThemeFlow
        )
    }
}

@Composable
fun BottomButtonsSection(
    ctx: Context,
    onSpanish: () -> Unit,
    onEnglish: () -> Unit,
    preferencesLanguageFlow: Flow<String>,
    onVolume: (newVolumeValue: Int) -> Unit,
    preferencesVolumeFlow: Flow<Int>,
    onTheme: (isDark: Boolean) -> Unit,
    preferencesThemeFlow: Flow<Boolean>
) {
    val buttonModifier = Modifier
        .padding(vertical = 8.dp)
        .fillMaxWidth()
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp)
            .verticalScroll(rememberScrollState()),
    ) {
        OutlineTextSection(
            stringResource(R.string.settings),
            textSize = MaterialTheme.typography.displayMedium.fontSize
        )
        Spacer(modifier = Modifier.height(16.dp))
        Logo(modifier = Modifier.align(Alignment.CenterHorizontally))
        LabeledSetting(stringResource(R.string.language))
        LanguageSettings(ctx, onSpanish, onEnglish, preferencesLanguageFlow)


        Spacer(modifier = Modifier.height(16.dp))
        LabeledSetting(stringResource(R.string.volume))

        VolumeSettings(ctx, onVolume, preferencesVolumeFlow)
        /*Spacer(modifier = Modifier.height(16.dp))
        Row(
            modifier = Modifier.padding(end = 32.dp),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.Start,
        ) {
            LabeledSetting(stringResource(R.string.theme))
            ThemeSettings(ctx, onTheme, preferencesThemeFlow)
        }*/
        //Spacer(modifier = Modifier.width(16.dp))
        //ThemeSettings(ctx, onTheme, preferencesThemeFlow)
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
fun LanguageSettings(
    ctx: Context,
    onSpanish: () -> Unit,
    onEnglish: () -> Unit,
    preferencesLanguageFlow: Flow<String>
) {
    val languageState by preferencesLanguageFlow.collectAsState(initial = "en")
    val buttonModifier = Modifier
        .padding(vertical = 8.dp)
        .fillMaxWidth()
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp)
            .padding(vertical = 8.dp),
    ) {
        ButtonItem(
            text = stringResource(R.string.english),
            onClick = { onEnglish() },
            modifier = buttonModifier,
            enabled = languageState != "en"
        )
        ButtonItem(
            text = stringResource(R.string.spanish),
            onClick = { onSpanish() },
            modifier = buttonModifier,
            enabled = languageState != "es"
        )
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
fun VolumeSettings(
    ctx: Context,
    onVolume: (newVolumeValue: Int) -> Unit,
    preferencesVolumeFlow: Flow<Int>,
) {
    val colorScheme = MaterialTheme.colorScheme
    ScapeTheAddsTheme {
        val volumeState by preferencesVolumeFlow.collectAsState(initial = 50)

        Column(
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Slider(
                value = volumeState.toFloat() / 100,
                onValueChange = {
                    onVolume((it * 100).toInt())
                }
            )
            Text(text = volumeState.toFloat().toString(), color = colorScheme.primary)
        }

    }
}

@Composable
fun ThemeSettings(
    ctx: Context,
    onTheme: (isDark: Boolean) -> Unit,
    preferencesThemeFlow: Flow<Boolean>
) {
    val themeState = preferencesThemeFlow.collectAsState(initial = false)

    Switch(
        checked = themeState.value,
        onCheckedChange = { darkBool -> onTheme(darkBool) },
    )
}

@Preview(showBackground = true)
@Composable
fun SettingsPreview() {
    ScapeTheAddsTheme {
        SettingsScreen(
            onExit = { println("Exit") },
            onEnglish = { println("English") },
            onSpanish = { println("English") },
            preferencesLanguageFlow = flow { "English" },
            onVolume = { println("Volume") },
            preferencesVolumeFlow = flow { 50 },
            onTheme = { println("Theme") },
            preferencesThemeFlow = flow { false }
        )
    }
}