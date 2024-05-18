package es.finders.scapetheads.ui.theme

import android.app.Activity
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.darkColorScheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.SideEffect
import androidx.compose.ui.graphics.toArgb
import androidx.compose.ui.platform.LocalView
import androidx.core.view.WindowCompat

// Updated color values for easier identification
private val DarkColorScheme = darkColorScheme(
    primary = PurplePrimary,
    secondary = PurpleGreySecondary,
    tertiary = PurpleBlackTertiary,
    background = PurpleGreyBackground,
    onPrimary = PurpleGreySecondary,
    onSecondary = PurplePrimary,
    onTertiary = PurpleGreyBackground,
    onBackground = PurpleBlackTertiary
)

private val LightColorScheme = lightColorScheme(
    primary = RedPrimary,
    secondary = WhiteSecondary,
    tertiary = RedBlackTertiary,
    background = BeigeBackground,
    onPrimary = WhiteSecondary,
    onSecondary = RedPrimary,
    onTertiary = BeigeBackground,
    onBackground = RedBlackTertiary
)

@Composable
fun ScapeTheAddsTheme(
    darkTheme: Boolean = isSystemInDarkTheme(),
    content: @Composable () -> Unit
) {
    val colorScheme = if (darkTheme) DarkColorScheme else LightColorScheme

    val view = LocalView.current
    if (!view.isInEditMode) {
        SideEffect {
            val window = (view.context as Activity).window
            window.statusBarColor = colorScheme.primary.toArgb()
            WindowCompat.getInsetsController(window, view).isAppearanceLightStatusBars = !darkTheme
        }
    }

    MaterialTheme(
        colorScheme = colorScheme,
        typography = Typography,
        content = content
    )
}
