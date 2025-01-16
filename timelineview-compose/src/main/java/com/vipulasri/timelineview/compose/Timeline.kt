package com.vipulasri.timelineview.compose

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.defaultMinSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.drawBehind
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.PathEffect
import androidx.compose.ui.graphics.drawscope.DrawScope
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp

@Composable
fun Timeline(
    modifier: Modifier = Modifier,
    type: TimelineType = TimelineType.MIDDLE,
    markerSize: Dp = TimelineDefaults.MarkerSize,
    markerColor: Color = TimelineDefaults.MarkerColor,
    lineStyle: LineStyle = LineStyle.Normal(),
    orientation: TimelineOrientation = TimelineOrientation.Vertical
) {
    Box(
        modifier = modifier
            .defaultMinSize(minWidth = markerSize, minHeight = markerSize)
            .drawTimeline(
                type = type,
                markerSize = markerSize,
                markerColor = markerColor,
                lineStyle = lineStyle,
                orientation = orientation
            )
    )
}

private fun Modifier.drawTimeline(
    type: TimelineType,
    markerSize: Dp,
    markerColor: Color,
    lineStyle: LineStyle,
    orientation: TimelineOrientation
) = drawBehind {
    // Draw marker
    drawCircle(
        color = markerColor,
        radius = markerSize.toPx() / 2f,
        center = center
    )

    if (type != TimelineType.SINGLE) {
        when (orientation) {
            TimelineOrientation.Vertical -> drawVerticalLines(type, markerSize, lineStyle)
            TimelineOrientation.Horizontal -> drawHorizontalLines(type, markerSize, lineStyle)
        }
    }
}

private fun DrawScope.drawVerticalLines(
    type: TimelineType,
    markerSize: Dp,
    lineStyle: LineStyle
) {
    when (type) {
        TimelineType.START -> drawEndLine(markerSize, lineStyle)
        TimelineType.END -> drawStartLine(markerSize, lineStyle)
        TimelineType.MIDDLE -> {
            drawStartLine(markerSize, lineStyle)
            drawEndLine(markerSize, lineStyle)
        }

        TimelineType.SINGLE -> { /* No lines */
        }
    }
}

private fun DrawScope.drawHorizontalLines(
    type: TimelineType,
    markerSize: Dp,
    lineStyle: LineStyle
) {
    when (type) {
        TimelineType.START -> drawEndLineHorizontal(markerSize, lineStyle)
        TimelineType.END -> drawStartLineHorizontal(markerSize, lineStyle)
        TimelineType.MIDDLE -> {
            drawStartLineHorizontal(markerSize, lineStyle)
            drawEndLineHorizontal(markerSize, lineStyle)
        }

        TimelineType.SINGLE -> { /* No lines for single item */
        }
    }
}

private fun DrawScope.drawStartLine(markerSize: Dp, lineStyle: LineStyle) {
    val startOffset = this.center - Offset(0f, markerSize.div(2).toPx())
    val endOffset = Offset(this.center.x, 0f)
    drawLine(
        color = lineStyle.color(),
        start = startOffset,
        end = endOffset,
        strokeWidth = lineStyle.width().toPx(),
        pathEffect = getPathEffect(lineStyle)
    )
}

private fun DrawScope.drawEndLine(markerSize: Dp, lineStyle: LineStyle) {
    val startOffset = this.center + Offset(0f, markerSize.div(2).toPx())
    val endOffset = Offset(this.center.x, this.size.height)
    drawLine(
        color = lineStyle.color(),
        start = startOffset,
        end = endOffset,
        strokeWidth = lineStyle.width().toPx(),
        pathEffect = getPathEffect(lineStyle)
    )
}

private fun DrawScope.drawStartLineHorizontal(markerSize: Dp, lineStyle: LineStyle) {
    val startOffset = this.center - Offset(markerSize.div(2).toPx(), 0f)
    val endOffset = Offset(0f, this.center.y)
    drawLine(
        color = lineStyle.color(),
        start = startOffset,
        end = endOffset,
        strokeWidth = lineStyle.width().toPx(),
        pathEffect = getPathEffect(lineStyle)
    )
}

private fun DrawScope.drawEndLineHorizontal(markerSize: Dp, lineStyle: LineStyle) {
    val startOffset = this.center + Offset(markerSize.div(2).toPx(), 0f)
    val endOffset = Offset(this.size.width, this.center.y)
    drawLine(
        color = lineStyle.color(),
        start = startOffset,
        end = endOffset,
        strokeWidth = lineStyle.width().toPx(),
        pathEffect = getPathEffect(lineStyle)
    )
}

private fun getPathEffect(lineStyle: LineStyle): PathEffect? {
    return if (lineStyle is LineStyle.Dashed) {
        PathEffect.dashPathEffect(
            floatArrayOf(
                lineStyle.dashLength.value,
                lineStyle.dashGap.value
            )
        )
    } else null
}

@Preview(showBackground = true)
@Composable
private fun TimelineVerticalPreview() {
    Column(Modifier.padding(16.dp)) {
        val totalItems = 4
        repeat(totalItems) { position ->
            Timeline(
                modifier = Modifier.height(100.dp),
                type = getTimelineType(position, totalItems),
                markerColor = Color(0xFF2196F3),
                lineStyle = LineStyle.Dashed(
                    color = Color(0xFF2196F3),
                    dashLength = 8.dp,
                    dashGap = 4.dp
                )
            )
        }
    }
}

@Preview(showBackground = true)
@Composable
private fun TimelineHorizontalPreview() {
    Row(
        modifier = Modifier.padding(16.dp)
    ) {
        val totalItems = 4
        repeat(totalItems) { position ->
            Timeline(
                modifier = Modifier.width(80.dp),
                type = getTimelineType(position, totalItems),
                orientation = TimelineOrientation.Horizontal,
                markerColor = Color(0xFF4CAF50),
                lineStyle = LineStyle.Normal(
                    color = Color(0xFF4CAF50),
                    width = 2.dp
                )
            )
        }
    }
}

@Preview(showBackground = true)
@Composable
private fun TimelineSingleItemPreview() {
    Column(Modifier.padding(16.dp)) {
        Timeline(
            type = TimelineType.START,
            markerColor = Color(0xFF2196F3),
            lineStyle = LineStyle.Dashed(
                color = Color(0xFF2196F3),
                dashLength = 4.dp,
                dashGap = 2.dp
            )
        )
    }
}