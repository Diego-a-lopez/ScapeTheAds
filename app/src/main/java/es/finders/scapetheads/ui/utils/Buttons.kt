package es.finders.scapetheads.ui.utils

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.layout.wrapContentWidth
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Clear
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.geometry.CornerRadius
import androidx.compose.ui.geometry.RoundRect
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Outline
import androidx.compose.ui.graphics.Path
import androidx.compose.ui.graphics.Shape
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Density
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.LayoutDirection
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.em
import es.finders.scapetheads.R
import es.finders.scapetheads.ui.theme.Kalam
import es.finders.scapetheads.ui.theme.ScapeTheAddsTheme

@Composable
fun SignInButton(
    onClick: () -> Unit,
    modifier: Modifier = Modifier.padding(start = 4.dp, end = 4.dp),
) {
    val colorScheme = MaterialTheme.colorScheme
    Button(
        onClick = onClick,
        modifier = modifier,
        shape = RoundedCornerShape(6.dp),
        colors = ButtonDefaults.buttonColors(
            containerColor = colorScheme.tertiary,
            contentColor = colorScheme.onTertiary
        )
    ) {
        Image(
            painter = painterResource(id = R.drawable.ic_logo_google),
            contentDescription = ""
        )
        Text(
            text = stringResource(R.string.sign_in_with_google),
            color = colorScheme.onTertiary,
            modifier = Modifier.padding(6.dp)
        )
    }
}

@Composable
fun ButtonItem(
    text: String,
    onClick: () -> Unit,
    modifier: Modifier,
    enabled: Boolean = true,
) {
    val colorScheme = MaterialTheme.colorScheme
    Button(
        onClick = onClick,
        modifier = modifier,
        enabled = enabled,
        shape = RoundedCornerShape(35),
        contentPadding = PaddingValues(top = 10.dp, bottom = 10.dp),
        border = BorderStroke(1.dp, colorScheme.tertiary),
        colors = ButtonDefaults.buttonColors(
            containerColor = if (enabled) colorScheme.primary else colorScheme.onPrimary
        )
    ) {
        Text(
            text = text,
            textAlign = TextAlign.Center,
            color = if (enabled) colorScheme.secondary else colorScheme.onSecondary,
            fontSize = 5.em,
            fontFamily = Kalam,
            fontWeight = FontWeight.Bold
        )
    }
}

@Composable
fun BackButton(
    onClick: () -> Unit,
    modifier: Modifier = Modifier,
) {
    val colorScheme = MaterialTheme.colorScheme
    Box(
        modifier = modifier
            .size(50.dp)
            .clip(CustomExitButton(cornerRadius = 5.dp))
            .background(colorScheme.background)
            .wrapContentHeight(Alignment.CenterVertically)
            .wrapContentWidth(Alignment.End)
    ) {
        Box(
            modifier = Modifier
                .height(43.dp)
                .width(46.dp)
                .clip(CustomExitButton(cornerRadius = 5.dp))
                .background(colorScheme.primary)
        ) {
            Column(
                Modifier
                    .fillMaxWidth()
                    .fillMaxHeight(),
                verticalArrangement = Arrangement.Center,
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Icon(
                    imageVector = Icons.Default.Clear,
                    contentDescription = "Back",
                    tint = colorScheme.background,
                    modifier = Modifier
                        .size(45.dp)
                        .clickable {
                            onClick()
                        }
                )
            }
        }
    }
}

@Composable
fun IconButtonItem(text: String, icon: Painter, onClick: () -> Unit, modifier: Modifier) {
    val colorScheme = MaterialTheme.colorScheme
    Button(
        onClick = { onClick() },
        modifier = modifier,
        shape = RoundedCornerShape(35),
        contentPadding = PaddingValues(top = 10.dp, bottom = 10.dp),
        border = BorderStroke(1.dp, Color.Black),
        colors = ButtonDefaults.buttonColors(
            containerColor = colorScheme.primary
        ),
    ) {
        Icon(
            painter = icon,
            contentDescription = text,
            tint = colorScheme.onPrimary,
            modifier = Modifier.padding(end = 6.dp)
        )
        Text(
            text = text,
            textAlign = TextAlign.Center,
            color = colorScheme.onPrimary,
            fontSize = 5.em,
            fontFamily = Kalam,
            fontWeight = FontWeight.Bold
        )
    }
}

@Composable
fun IconButtonItem(
    text: String,
    icon: ImageVector,
    onClick: () -> Unit,
    modifier: Modifier,
) {
    val colorScheme = MaterialTheme.colorScheme
    Button(
        onClick = onClick,
        modifier = modifier,
        shape = RoundedCornerShape(35),
        contentPadding = PaddingValues(top = 10.dp, bottom = 10.dp),
        border = BorderStroke(1.dp, Color.Black),
        colors = ButtonDefaults.buttonColors(
            containerColor = colorScheme.primary
        )
    ) {
        Icon(
            imageVector = icon,
            contentDescription = text,
            tint = Color.White,
            modifier = Modifier.padding(end = 6.dp)
        )
        Text(
            text = text,
            textAlign = TextAlign.Center,
            color = Color.White,
            fontSize = 5.em,
            fontFamily = Kalam,
            fontWeight = FontWeight.Bold
        )
    }
}

@Composable
fun IconOnlyButtonItem(
    text: String,
    icon: ImageVector,
    onClick: () -> Unit,
    modifier: Modifier,
) {
    val colorScheme = MaterialTheme.colorScheme
    Button(
        onClick = onClick,
        modifier = modifier,
        shape = RoundedCornerShape(35),
        contentPadding = PaddingValues(top = 10.dp, bottom = 10.dp),
        border = BorderStroke(1.dp, colorScheme.tertiary),
        colors = ButtonDefaults.buttonColors(
            containerColor = colorScheme.primary
        )
    ) {
        Icon(
            imageVector = icon,
            contentDescription = text,
            tint = colorScheme.onPrimary,
            modifier = Modifier
        )
    }
}

@Preview
@Composable
fun ExitShape() {
    ScapeTheAddsTheme {
        BackButton({})
    }
}

class CustomExitButton(
    private val cornerRadius: Dp = 5.dp,
) : Shape {
    override fun createOutline(
        size: Size,
        layoutDirection: LayoutDirection,
        density: Density
    ): Outline {
        val cornerRadius = with(density) { cornerRadius.toPx() }
        val path = Path().apply {
            addRoundRect(
                RoundRect(
                    cornerRadius = CornerRadius(
                        x = cornerRadius,
                        y = cornerRadius
                    ),
                    left = 0f,
                    top = 0f,
                    right = size.width,
                    bottom = size.height
                )
            )

            moveTo(
                x = size.width / 3,
                y = size.height
            )

            lineTo(
                x = size.width,
                y = size.height
            )

            lineTo(
                x = size.width,
                y = 0f
            )

            lineTo(
                x = size.width / 3,
                y = 0f
            )

            close()
        }
        return Outline.Generic(path)
    }
}
