package com.cryptoemergency.cryptoemergency.providers.theme

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.size
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.geometry.Rect
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Outline
import androidx.compose.ui.graphics.Path
import androidx.compose.ui.graphics.Shape
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Density
import androidx.compose.ui.unit.LayoutDirection
import androidx.compose.ui.unit.dp
import kotlin.math.PI
import kotlin.math.cos
import kotlin.math.min
import kotlin.math.sin
import kotlin.math.sqrt

val shapes = CommonShape(
    hexagonShape = HexagonShape(),
    starShape = StarShape(),
    diamondShape = DiamondShape(),
    ticketShape = TicketShape(),
)

private class HexagonShape : Shape {
    override fun createOutline(
        size: Size,
        layoutDirection: LayoutDirection,
        density: Density
    ): Outline {
        val path = Path().apply {
            val radius = min(size.width / 2f, size.height / 2f)
            val triangleHeight = (sqrt(3.0) * radius / 2)
            val centerX = size.width / 2
            val centerY = size.height / 2

            moveTo(x = centerX, y = centerY + radius)
            lineTo(x = (centerX - triangleHeight).toFloat(), y = centerY + radius / 2)
            lineTo(x = (centerX - triangleHeight).toFloat(), y = centerY - radius / 2)
            lineTo(x = centerX, y = centerY - radius)
            lineTo(x = (centerX + triangleHeight).toFloat(), y = centerY - radius / 2)
            lineTo(x = (centerX + triangleHeight).toFloat(), y = centerY + radius / 2)

            close()
        }

        return Outline.Generic(path)
    }
}

private class StarShape : Shape {
    override fun createOutline(
        size: Size,
        layoutDirection: LayoutDirection,
        density: Density
    ): Outline {
        val path = Path().apply {
            val numPoints = 5
            val centerX = size.width / 2f
            val centerY = size.height / 2f
            val outerRadius = min(size.width, size.height) / 2f
            val innerRadius = outerRadius / 2.5f

            val doublePi = 2 * PI
            val angleIncrement = doublePi / numPoints

            var angle = -PI / 2f
            moveTo(
                x = (centerX + outerRadius * cos(angle)).toFloat(),
                y = (centerY + outerRadius * sin(angle)).toFloat()
            )

            for (i in 1..numPoints) {
                angle += angleIncrement / 2
                lineTo(
                    x = (centerX + innerRadius * cos(angle)).toFloat(),
                    y = (centerY + innerRadius * sin(angle)).toFloat()
                )
                angle += angleIncrement / 2
                lineTo(
                    x = (centerX + outerRadius * cos(angle)).toFloat(),
                    y = (centerY + outerRadius * sin(angle)).toFloat()
                )
            }

            close()
        }
        return Outline.Generic(path)
    }
}

private class TicketShape : Shape {
    override fun createOutline(
        size: Size,
        layoutDirection: LayoutDirection,
        density: Density
    ): Outline {
        val path = Path().apply {
            val cornerRadius = 70f
            // Top left arc
            arcTo(
                rect = Rect(
                    left = -cornerRadius,
                    top = -cornerRadius,
                    right = cornerRadius,
                    bottom = cornerRadius
                ),
                startAngleDegrees = 90.0f,
                sweepAngleDegrees = -90.0f,
                forceMoveTo = false
            )
            lineTo(x = size.width - cornerRadius, y = 0f)
            // Top right arc
            arcTo(
                rect = Rect(
                    left = size.width - cornerRadius,
                    top = -cornerRadius,
                    right = size.width + cornerRadius,
                    bottom = cornerRadius
                ),
                startAngleDegrees = 180.0f,
                sweepAngleDegrees = -90.0f,
                forceMoveTo = false
            )
            lineTo(x = size.width, y = size.height - cornerRadius)
            // Bottom right arc
            arcTo(
                rect = Rect(
                    left = size.width - cornerRadius,
                    top = size.height - cornerRadius,
                    right = size.width + cornerRadius,
                    bottom = size.height + cornerRadius
                ),
                startAngleDegrees = 270.0f,
                sweepAngleDegrees = -90.0f,
                forceMoveTo = false
            )
            lineTo(x = cornerRadius, y = size.height)
            // Bottom left arc
            arcTo(
                rect = Rect(
                    left = -cornerRadius,
                    top = size.height - cornerRadius,
                    right = cornerRadius,
                    bottom = size.height + cornerRadius
                ),
                startAngleDegrees = 0.0f,
                sweepAngleDegrees = -90.0f,
                forceMoveTo = false
            )
            lineTo(x = 0f, y = cornerRadius)

            close()
        }
        return Outline.Generic(path)
    }
}

private class DiamondShape : Shape {
    override fun createOutline(
        size: Size,
        layoutDirection: LayoutDirection,
        density: Density
    ): Outline {
        val path = Path().apply {
            val centerX = size.width / 2f
            val diamondCurve = 60f
            val width = size.width
            val height = size.height

            moveTo(x = 0f + diamondCurve, y = 0f)
            lineTo(x = width - diamondCurve, y = 0f)
            lineTo(x = width, y = diamondCurve)
            lineTo(x = centerX, y = height)
            lineTo(x = 0f, y = diamondCurve)

            close()
        }
        return Outline.Generic(path)
    }
}

@Preview
@Composable
private fun Preview() {
    val hexagonShape = shapes.hexagonShape
    val starShape = shapes.starShape
    val diamondShape = shapes.diamondShape
    val ticketShape = shapes.ticketShape

    Column {
        Box(
            Modifier
                .size(80.dp)
                .clip(hexagonShape)
                .background(Color.Red)
        )


        Box(
            Modifier
                .size(80.dp)
                .clip(starShape)
                .background(Color.Red)
        )

        Box(
            Modifier
                .size(80.dp)
                .clip(diamondShape)
                .background(Color.Red)
        )

        Box(
            Modifier
                .size(270.dp, 160.dp)
                .clip(ticketShape)
                .background(Color.Red)
        )
    }
}
