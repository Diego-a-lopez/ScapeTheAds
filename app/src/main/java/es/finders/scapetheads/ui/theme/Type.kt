package es.finders.scapetheads.ui.theme

import androidx.compose.material3.Typography
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.sp
import es.finders.scapetheads.R

val Kalam = FontFamily(
    Font(R.font.kalam_regular),
    Font(R.font.kalam_light, FontWeight.Light),
    Font(R.font.kalam_bold, FontWeight.Bold),
)


val Merienda = FontFamily(
    Font(R.font.merienda),
)

// Set of Material typography styles to start with
val Typography = Typography(
    bodyLarge = TextStyle(
        fontFamily = Kalam,
        fontWeight = FontWeight.Bold,
        fontSize = 16.sp,
        lineHeight = 28.sp,
        letterSpacing = 0.5.sp,
        color = RedPrimary,

        ),
    titleLarge = TextStyle(
        fontFamily = Kalam,
        fontWeight = FontWeight.Normal,
        fontSize = 22.sp,
        lineHeight = 28.sp,
        letterSpacing = 0.sp
    ),
    labelSmall = TextStyle(
        fontFamily = Kalam,
        fontWeight = FontWeight.Medium,
        fontSize = 11.sp,
        lineHeight = 16.sp,
        letterSpacing = 0.5.sp
    )
)