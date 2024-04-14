package es.finders.scapetheads.ui.utils

import androidx.compose.foundation.Image
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import es.finders.scapetheads.R

@Composable
fun Title(modifier: Modifier = Modifier) {
    Image(
        painter = painterResource(id = R.drawable.title),
        contentDescription = null,
        contentScale = ContentScale.Inside,
        modifier = modifier,
    )
}