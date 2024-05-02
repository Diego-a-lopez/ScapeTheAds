package es.finders.scapetheads.ui.utils

import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.LocalTextStyle
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.TextFieldColors
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Shape
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.input.VisualTransformation

@Composable
fun TextInput(
    text: String,
    onTextChange: (String) -> Unit,
    placeholder: @Composable (() -> Unit)?,
    modifier: Modifier = Modifier,
    colors: TextFieldColors = TextFieldDefaults.colors(
        focusedContainerColor = Color.White,
        unfocusedContainerColor = Color.White
    ),
    textStyle: TextStyle = LocalTextStyle.current,
    shape: Shape = RoundedCornerShape(35),
    visualTransformation: VisualTransformation = VisualTransformation.None
) {
    OutlinedTextField(
        value = text,
        onValueChange = onTextChange,
        modifier = modifier,
        placeholder = placeholder,
        colors = colors,
        textStyle = textStyle,
        shape = shape,
        visualTransformation = visualTransformation
    )
}