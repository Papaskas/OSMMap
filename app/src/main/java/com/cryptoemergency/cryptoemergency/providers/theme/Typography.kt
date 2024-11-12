package com.cryptoemergency.cryptoemergency.providers.theme

import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.sp
import com.cryptoemergency.cryptoemergency.R

val sfUiTextFontFamily = FontFamily(
    Font(R.font.sfuitext__regular, FontWeight.Normal),
    Font(R.font.sfuitext__semibold, FontWeight.SemiBold),
    Font(R.font.sfuitext__bold, FontWeight.Bold),
)

val manropeFontFamily = FontFamily(
    Font(R.font.manrope__extra_light, FontWeight.ExtraLight),
    Font(R.font.manrope__light, FontWeight.Light),
    Font(R.font.manrope__regular, FontWeight.Normal),
    Font(R.font.manrope__medium, FontWeight.Medium),
    Font(R.font.manrope__semi_bold, FontWeight.SemiBold),
    Font(R.font.manrope__bold, FontWeight.Bold),
    Font(R.font.manrope__extra_bold, FontWeight.ExtraBold),
)

val typography = Typography(
    h1 = TextStyle(
        fontFamily = sfUiTextFontFamily,
        fontWeight = FontWeight.Bold,
        fontSize = 28.sp,
        letterSpacing = 28.sp * (-2 / 100f),
    ),
    h2 = TextStyle(
        fontFamily = sfUiTextFontFamily,
        fontWeight = FontWeight.Bold,
        fontSize = 18.sp,
        letterSpacing = 18.sp * (-2 / 100f),
        lineHeight = 26.sp,
    ),
    h3 = TextStyle(
        fontFamily = sfUiTextFontFamily,
        fontWeight = FontWeight.SemiBold,
        fontSize = 15.sp,
        letterSpacing = 15.sp * (-2 / 100f),
        lineHeight = 18.sp,
    ),
    h4 = TextStyle(
        fontFamily = sfUiTextFontFamily,
        fontWeight = FontWeight.SemiBold,
        fontSize = 13.sp,
        letterSpacing = 13.sp * (-2 / 100f),
        lineHeight = 16.sp,
    ),
    body1 = TextStyle(
        fontFamily = sfUiTextFontFamily,
        fontWeight = FontWeight.Normal,
        fontSize = 15.sp,
        letterSpacing = 15.sp * (-2 / 100f),
        lineHeight = 22.sp,
    ),
    caption1 = TextStyle(
        fontFamily = sfUiTextFontFamily,
        fontWeight = FontWeight.Normal,
        fontSize = 13.sp,
        letterSpacing = 13.sp * (-2 / 100f),
        lineHeight = 16.sp,
    ),
    caption2 = TextStyle(
        fontFamily = sfUiTextFontFamily,
        fontWeight = FontWeight.Normal,
        fontSize = 12.sp,
        letterSpacing = 12.sp * (-2 / 100f),
        lineHeight = 16.sp,
    ),
    subscribesCount = TextStyle(
        fontFamily = manropeFontFamily,
        fontWeight = FontWeight.Bold,
        fontSize = 17.sp,
        lineHeight = 24.sp,
    ),
    helloText = TextStyle(
        fontFamily = manropeFontFamily,
        fontWeight = FontWeight.ExtraBold,
        fontSize = 24.sp,
        letterSpacing = 24.sp * (-2 / 100f),
        lineHeight =  32.78.sp,
    ),
)
