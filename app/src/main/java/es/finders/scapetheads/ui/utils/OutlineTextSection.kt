package es.finders.scapetheads.ui.utils

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.LocalTextStyle
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.StrokeJoin
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.TextUnit
import androidx.compose.ui.unit.dp
import es.finders.scapetheads.ui.theme.RedPrimary

@Composable
fun OutlineTextSection(
    text: String,
    textAlign: TextAlign = TextAlign.Center,
    textColor: Color = RedPrimary, // default color for main text
    outlineTextColor: Color = Color.Black, // default color for outline text
    textSize: TextUnit = MaterialTheme.typography.displaySmall.fontSize, // default size
    contentAlignment: Alignment = Alignment.Center,
    modifier: Modifier = Modifier,
) {
    val colorScheme = MaterialTheme.colorScheme
    Box(
        modifier = modifier.fillMaxWidth(),
        contentAlignment = contentAlignment
    ) {
        Text(
            text = text,
            modifier = Modifier.padding(horizontal = 16.dp),
            textAlign = textAlign,
            style = LocalTextStyle.current.merge(
                TextStyle(
                    color = colorScheme.primary,
                    fontSize = textSize
                )
            ),
        )
        Text(
            text = text,
            modifier = Modifier.padding(horizontal = 16.dp),
            textAlign = textAlign,
            style = LocalTextStyle.current.merge(
                TextStyle(
                    color = colorScheme.tertiary,
                    fontSize = textSize,
                    drawStyle = Stroke(width = 4f, join = StrokeJoin.Round)
                )
            )
        )
    }
}