package com.cryptoemergency.cryptoemergency.providers.theme

import androidx.annotation.DrawableRes
import androidx.compose.runtime.Composable
import androidx.compose.runtime.ReadOnlyComposable
import androidx.compose.runtime.staticCompositionLocalOf
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Shape
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.unit.Dp

data class Colors(
    val accent: Color,
    val logoPink: Color,
    val logoBlue: Color,

    val buttonText: Color = Color(0xFFFFFFFF),

    val text1: Color,
    val text2: Color,
    val text3: Color,
    val text4: Color,
    val text5: Color,
    val text6: Color,

    val stroke: Color,
    val strokeVariant: Color,

    val backgroundMain: Color,
    val background2: Color,
    val background3: Color,
    val background4: Color,

    val surface1: Color,
    val surface2: Color,
    val surface3: Color,

    val success: Color,
    val error: Color,
)

data class Typography(
    val h1: TextStyle,
    val h2: TextStyle,
    val h3: TextStyle,
    val h4: TextStyle,
    val body1: TextStyle,
    val caption1: TextStyle,
    val caption2: TextStyle,
    val subscribesCount: TextStyle,
    val helloText: TextStyle,
)

data class CommonShape(
    val hexagonShape: Shape,
    val diamondShape: Shape,
    val ticketShape: Shape,
    val starShape: Shape,
)

data class Dimens(
    val padding: Dp,
    val radius: Dp,
)

data class Icons(
    @DrawableRes val hexagonOnMainMenu: Int,
    @DrawableRes val logo: Int,
    @DrawableRes val theme: Int,
)

object Theme {
    val colors: Colors
        @Composable
        @ReadOnlyComposable
        get() = LocalColors.current

    val typography: Typography
        @Composable
        @ReadOnlyComposable
        get() = LocalTypography.current

    val shapes: CommonShape
        @Composable
        @ReadOnlyComposable
        get() = LocalShape.current

    val icons: Icons
        @Composable
        @ReadOnlyComposable
        get() = LocalIcons.current

    val dimens: Dimens
        @Composable
        @ReadOnlyComposable
        get() = LocalDimens.current
}

val LocalColors =
    staticCompositionLocalOf<Colors> {
        error("No colors provided")
    }

val LocalTypography =
    staticCompositionLocalOf<Typography> {
        error("No typography provided")
    }

val LocalShape =
    staticCompositionLocalOf<CommonShape> {
        error("No shapes provided")
    }

val LocalIcons =
    staticCompositionLocalOf<Icons> {
        error("No icons provided")
    }

val LocalDimens =
    staticCompositionLocalOf<Dimens> {
        error("No dimens provided")
    }

