package com.vipulasri.timelineview.compose

import androidx.compose.runtime.Immutable
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.PathEffect
import androidx.compose.ui.unit.Dp

@Immutable
data class LineStyle internal constructor(
    val color: Color = TimelineDefaults.LineColor,
    val width: Dp = TimelineDefaults.LineWidth,
    val pathEffect: PathEffect? = null
) {
    companion object {
        fun solid(
            color: Color = TimelineDefaults.LineColor,
            width: Dp = TimelineDefaults.LineWidth
        ) = LineStyle(color, width)

        fun dashed(
            color: Color = TimelineDefaults.LineColor,
            width: Dp = TimelineDefaults.LineWidth,
            dashLength: Dp = TimelineDefaults.LineDashLength,
            dashGap: Dp = TimelineDefaults.LineDashGap
        ) = LineStyle(
            color, width, PathEffect.dashPathEffect(
                floatArrayOf(dashLength.value, dashGap.value)
            )
        )
    }
}