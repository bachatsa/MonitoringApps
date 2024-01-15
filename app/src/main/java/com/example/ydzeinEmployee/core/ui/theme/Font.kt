package com.example.ydzeinEmployee.core.ui.theme

import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.PlatformTextStyle
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.unit.sp
import com.ydzmobile.employee.R

private val poppinsCustomFont = FontFamily(
    Font(R.font.poppins_thin, FontWeight.Thin, FontStyle.Normal),
    Font(R.font.poppins_thinitalic, FontWeight.Thin, FontStyle.Italic),

    Font(R.font.poppins_extralight, FontWeight.ExtraLight, FontStyle.Normal),
    Font(R.font.poppins_extralightitalic, FontWeight.ExtraLight, FontStyle.Italic),

    Font(R.font.poppins_light, FontWeight.Light, FontStyle.Normal),
    Font(R.font.poppins_lightitalic, FontWeight.Light, FontStyle.Italic),

    Font(R.font.poppins_regular, FontWeight.Normal, FontStyle.Normal),
    Font(R.font.poppins_italic, FontWeight.Normal, FontStyle.Italic),

    Font(R.font.poppins_medium, FontWeight.Medium, FontStyle.Normal),
    Font(R.font.poppins_mediumitalic, FontWeight.Medium, FontStyle.Italic),

    Font(R.font.poppins_semibold, FontWeight.SemiBold, FontStyle.Normal),
    Font(R.font.poppins_semibolditalic, FontWeight.SemiBold, FontStyle.Italic),

    Font(R.font.poppins_bold, FontWeight.Bold, FontStyle.Normal),
    Font(R.font.poppins_bolditalic, FontWeight.Bold, FontStyle.Italic),

    Font(R.font.poppins_extrabold, FontWeight.Bold, FontStyle.Normal),
    Font(R.font.poppins_extrabolditalic, FontWeight.Bold, FontStyle.Italic),

    Font(R.font.poppins_black, FontWeight.Black, FontStyle.Normal),
    Font(R.font.poppins_blackitalic, FontWeight.Black, FontStyle.Italic),
)

fun poppinsFont(
    size: Int = 16,
    color: Color = darkJungleGreen,
    fontStyle: FontStyle = FontStyle.Normal,
    fontWeight: Int = 400,
    isStringThrough: Boolean = false
): TextStyle {
    return TextStyle(
        fontFamily = poppinsCustomFont,
        fontStyle = fontStyle,
        fontWeight = FontWeight(fontWeight),
        fontSize = size.sp,
        color = color,
        textDecoration =
        if (isStringThrough) TextDecoration.LineThrough else null,
        platformStyle = PlatformTextStyle(includeFontPadding = false)
    )
}