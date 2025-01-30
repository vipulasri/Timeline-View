package com.vipulasri.timelineview.compose

import androidx.compose.runtime.Immutable
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.PathEffect
import androidx.compose.ui.unit.Dp

@Immutable
data class LineStyle(
    val startLine: Line,
    val endLine: Line
) {
    companion object {
        fun solid(
            color: Color = TimelineDefaults.LineColor,
            width: Dp = TimelineDefaults.LineWidth
        ) = LineStyle(
            startLine = Line(color, width),
            endLine = Line(color, width)
        )

        fun dashed(
            color: Color = TimelineDefaults.LineColor,
            width: Dp = TimelineDefaults.LineDashWidth,
            dashLength: Dp = TimelineDefaults.LineDashLength,
            dashGap: Dp = TimelineDefaults.LineDashGap
        ) = LineStyle(
            startLine = Line(
                color,
                width,
                PathEffect.dashPathEffect(
                    floatArrayOf(dashLength.value, dashGap.value)
                )
            ),
            endLine = Line(
                color,
                width,
                PathEffect.dashPathEffect(
                    floatArrayOf(dashLength.value, dashGap.value)
                )
            )
        )
    }
}

@Immutable
data class Line internal constructor(
    val color: Color = TimelineDefaults.LineColor,
    val width: Dp = TimelineDefaults.LineWidth,
    val pathEffect: PathEffect? = null
) {
    companion object {
        fun solid(
            color: Color = TimelineDefaults.LineColor,
            width: Dp = TimelineDefaults.LineWidth
        ) = Line(color, width)

        fun dashed(
            color: Color = TimelineDefaults.LineColor,
            width: Dp = TimelineDefaults.LineDashWidth,
            dashLength: Dp = TimelineDefaults.LineDashLength,
            dashGap: Dp = TimelineDefaults.LineDashGap
        ) = Line(
            color,
            width,
            PathEffect.dashPathEffect(
                floatArrayOf(dashLength.value, dashGap.value)
            )
        )
    }
}