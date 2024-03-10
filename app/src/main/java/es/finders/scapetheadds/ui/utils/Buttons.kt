package es.finders.scapetheadds.ui.utils

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.em
import es.finders.scapetheadds.ui.theme.RedPrimary

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
        Text(
            text = text,
            textAlign = TextAlign.Center,
            color = Color.White,
            fontSize = 5.em,
        )
    }
}

@Composable
fun BackButton() {
    Icon(
        imageVector = Icons.Default.ArrowBack,
        contentDescription = "Back",
        modifier = Modifier
            .size(48.dp)
            .clickable {
                // Handle navigation back to home screen
            }
    )
}