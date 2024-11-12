package com.cryptoemergency.cryptoemergency.modifiers

import androidx.compose.foundation.pager.PagerState
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.util.lerp
import kotlin.math.absoluteValue

/**
 * @see <a href="https://github.com/ryanw-mobile/compose-pager-demo">Github</a>
 */
fun Modifier.swiperAnimation(
    state: PagerState,
    pageIndex: Int,
): Modifier {
    return this then Modifier.graphicsLayer {
        val pageOffset = (
                (state.currentPage - pageIndex) + state
                    .currentPageOffsetFraction
                )

        alpha = lerp(
            start = 0.4f,
            stop = 1f,
            fraction = 1f - pageOffset.absoluteValue.coerceIn(0f, 1f),
        )

        cameraDistance = 8 * density
        rotationY = lerp(
            start = 0f,
            stop = 40f,
            fraction = pageOffset.coerceIn(-1f, 1f),
        )

        lerp(
            start = 0.5f,
            stop = 1f,
            fraction = 1f - pageOffset.absoluteValue.coerceIn(0f, 1f),
        ).also { scale ->
            scaleX = scale
            scaleY = scale
        }
    }
}