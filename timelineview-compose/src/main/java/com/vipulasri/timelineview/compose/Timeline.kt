package com.vipulasri.timelineview.compose

import androidx.compose.foundation.layout.Box
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.drawWithCache
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.drawscope.DrawScope
import androidx.compose.ui.unit.Dp

@Composable
fun Timeline(
    modifier: Modifier = Modifier,
    lineType: LineType = LineType.MIDDLE,
    lineStyle: LineStyle = LineStyle.solid(),
    orientation: TimelineOrientation = TimelineOrientation.Vertical,
    marker: @Composable () -> Unit,
) {
    Box(
        modifier = modifier
            .drawTimeline(
                lineType = lineType,
                lineStyle = lineStyle,
                orientation = orientation
            ),
        contentAlignment = Alignment.Center
    ) {
        marker()
    }
}

private fun Modifier.drawTimeline(
    lineType: LineType,
    lineStyle: LineStyle,
    orientation: TimelineOrientation
) = drawWithCache {
    // Get the marker size from the layout size
    val markerSize = minOf(size.width, size.height).toDp()

    onDrawWithContent {
        drawContent()

        if (lineType != LineType.SINGLE) {
            when (orientation) {
                TimelineOrientation.Vertical -> drawVerticalLines(lineType, markerSize, lineStyle)
                TimelineOrientation.Horizontal -> drawHorizontalLines(
                    lineType,
                    markerSize,
                    lineStyle
                )
            }
        }
    }
}

private fun DrawScope.drawVerticalLines(
    lineType: LineType,
    markerSize: Dp,
    lineStyle: LineStyle
) {
    when (lineType) {
        LineType.START -> drawEndLine(markerSize, lineStyle)
        LineType.END -> drawStartLine(markerSize, lineStyle)
        LineType.MIDDLE -> {
            drawStartLine(markerSize, lineStyle)
            drawEndLine(markerSize, lineStyle)
        }

        LineType.SINGLE -> { /* No lines */
        }
    }
}

private fun DrawScope.drawHorizontalLines(
    lineType: LineType,
    markerSize: Dp,
    lineStyle: LineStyle
) {
    when (lineType) {
        LineType.START -> drawEndLineHorizontal(markerSize, lineStyle)
        LineType.END -> drawStartLineHorizontal(markerSize, lineStyle)
        LineType.MIDDLE -> {
            drawStartLineHorizontal(markerSize, lineStyle)
            drawEndLineHorizontal(markerSize, lineStyle)
        }

        LineType.SINGLE -> { /* No lines for single item */
        }
    }
}

private fun DrawScope.drawStartLine(markerSize: Dp, lineStyle: LineStyle) {
    val startOffset = this.center - Offset(0f, markerSize.div(2).toPx())
    val endOffset = Offset(this.center.x, 0f)
    drawLine(
        color = lineStyle.color,
        start = startOffset,
        end = endOffset,
        strokeWidth = lineStyle.width.toPx(),
        pathEffect = lineStyle.pathEffect
    )
}

private fun DrawScope.drawEndLine(markerSize: Dp, lineStyle: LineStyle) {
    val startOffset = this.center + Offset(0f, markerSize.div(2).toPx())
    val endOffset = Offset(this.center.x, this.size.height)
    drawLine(
        color = lineStyle.color,
        start = startOffset,
        end = endOffset,
        strokeWidth = lineStyle.width.toPx(),
        pathEffect = lineStyle.pathEffect
    )
}

private fun DrawScope.drawStartLineHorizontal(markerSize: Dp, lineStyle: LineStyle) {
    val startOffset = this.center - Offset(markerSize.div(2).toPx(), 0f)
    val endOffset = Offset(0f, this.center.y)
    drawLine(
        color = lineStyle.color,
        start = startOffset,
        end = endOffset,
        strokeWidth = lineStyle.width.toPx(),
        pathEffect = lineStyle.pathEffect
    )
}

private fun DrawScope.drawEndLineHorizontal(markerSize: Dp, lineStyle: LineStyle) {
    val startOffset = this.center + Offset(markerSize.div(2).toPx(), 0f)
    val endOffset = Offset(this.size.width, this.center.y)
    drawLine(
        color = lineStyle.color,
        start = startOffset,
        end = endOffset,
        strokeWidth = lineStyle.width.toPx(),
        pathEffect = lineStyle.pathEffect
    )
}