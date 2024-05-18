package es.finders.scapetheads.ui.utils

import androidx.compose.foundation.Image
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import es.finders.scapetheads.R

@Composable
fun Logo(modifier: Modifier = Modifier) {
    val darkTheme: Boolean = isSystemInDarkTheme()
    if (darkTheme) {
        Image(
            painter = painterResource(id = R.drawable.logo),
            contentDescription = null,
            contentScale = ContentScale.None,
            modifier = modifier
        )
    } else {
        Image(
            painter = painterResource(id = R.drawable.logo),
            contentDescription = null,
            contentScale = ContentScale.None,
            modifier = modifier
        )
    }
}