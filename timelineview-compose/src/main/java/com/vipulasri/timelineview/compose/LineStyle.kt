package com.vipulasri.timelineview.compose

import androidx.compose.runtime.Immutable
import androidx.compose.runtime.Stable
import androidx.compose.ui.graphics.Color
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
            startLine = SolidLine(color, width),
            endLine = SolidLine(color, width)
        )

        fun dashed(
            color: Color = TimelineDefaults.LineColor,
            width: Dp = TimelineDefaults.LineDashWidth,
            dashLength: Dp = TimelineDefaults.LineDashLength,
            dashGap: Dp = TimelineDefaults.LineDashGap
        ) = LineStyle(
            startLine = DashedLine(
                color,
                width,
                dashLength,
                dashGap
            ),
            endLine = DashedLine(
                color,
                width,
                dashLength,
                dashGap
            )
        )
    }
}

@Stable
abstract class Line internal constructor(
    open val color: Color,
    open val width: Dp,
)

@Immutable
data class SolidLine(
    override val color: Color = TimelineDefaults.LineColor,
    override val width: Dp = TimelineDefaults.LineWidth,
) : Line(color, width)

@Immutable
data class DashedLine(
    override val color: Color = TimelineDefaults.LineColor,
    override val width: Dp = TimelineDefaults.LineDashWidth,
    val dashLength: Dp = TimelineDefaults.LineDashLength,
    val dashGap: Dp = TimelineDefaults.LineDashGap,
) : Line(color, width)