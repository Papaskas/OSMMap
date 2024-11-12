package com.cryptoemergency.cryptoemergency.providers.theme

import androidx.compose.ui.graphics.Color

val darkPalette = Colors(
    accent = DarkColors.accent,
    logoPink = DarkColors.logoPink,
    logoBlue = DarkColors.logoBlue,

    text1 = DarkColors.W90,
    text2 = DarkColors.G40,
    text3 = DarkColors.G100,
    text4 = DarkColors.G20,
    text5 = DarkColors.G20,
    text6 = DarkColors.W100,

    stroke = DarkColors.StrokeB,
    strokeVariant = DarkColors.StrokeB,

    backgroundMain = DarkColors.BgB100,
    background2 = DarkColors.BgB90,
    background3 = DarkColors.BgG100,
    background4 = DarkColors.StrokeB,

    surface1 = DarkColors.BgB80,
    surface2 = DarkColors.BgG100,
    surface3 = DarkColors.BgB80,

    success = DarkColors.success,
    error = DarkColors.error,
)

val lightPalette = Colors(
    accent = LightColors.accent,

    logoPink = LightColors.logoPink,
    logoBlue = LightColors.logoBlue,

    text1 = LightColors.B100,
    text2 = LightColors.G,
    text3 = LightColors.G,
    text4 = LightColors.G,
    text5 = LightColors.B90,
    text6 = LightColors.B100,

    stroke = LightColors.StrokeB20,
    strokeVariant = LightColors.StrokeB30,

    backgroundMain = LightColors.BgW100,
    background2 = LightColors.BgW90,
    background3 = LightColors.BgW90,
    background4 = LightColors.BgG,

    surface1 = LightColors.BgG,
    surface2 = LightColors.BgG,
    surface3 = LightColors.BgW90,

    success = LightColors.success,
    error = LightColors.error,
)

object DarkColors {
    val accent = Color(0xFFAB55FF)

    val logoPink = Color(0xFFC126CE)
    val logoBlue = Color(0xFF284CCB)

    val W100 = Color(0xFFFFFFFF)
    val W90 = Color(0xFFF8F8FA)
    val G100 = Color(0xFF5B5D5D)
    val G20 = Color(0xFFC4C8C7)
    val G40 = Color(0xFF878A89)

    val BgB100 = Color(0xFF151516)
    val BgB90 = Color(0xFF161617)
    val BgB80 = Color(0xFF212121)
    val BgG100 = Color(0xFF2A2A2A)

    val StrokeB = Color(0xFF272728)

    val error = Color(0xFFFF5555)
    val success = Color(0xFF6ED960)
}

object LightColors {
    val accent = Color(0xFF8E4BFF)

    val logoPink = Color(0xFFC126CE)
    val logoBlue = Color(0xFF284CCB)

    val W100 = Color(0xFFFFFFFF)
    val B100 = Color(0xFF343434)
    val B90 = Color(0xFF474C4F)
    val G = Color(0xFF999FAB)

    val BgW100 = Color(0xFFFFFFFF)
    val BgW90 = Color(0xFFFCFCFC)
    val BgG = Color(0xFFF4F4F4)

    val StrokeB20 = Color(0xFFEFEFEF)
    val StrokeB30 = Color(0xFFE7E8EA)

    val error = Color(0xFFF34942)
    val success = Color(0xFF0AB258)
}
