package com.vipulasri.timelineview.compose

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Star
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.drawWithCache
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.PathEffect
import androidx.compose.ui.graphics.drawscope.DrawScope
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp

@Composable
fun Timeline(
    modifier: Modifier = Modifier,
    lineType: LineType = LineType.MIDDLE,
    lineStyle: LineStyle = LineStyle.Normal(),
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
                TimelineOrientation.Horizontal -> drawHorizontalLines(lineType, markerSize, lineStyle)
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
                lineType = getLineType(position, totalItems),
                lineStyle = LineStyle.Dashed(
                    color = Color(0xFF2196F3),
                    width = 3.dp,
                    dashLength = 8.dp,
                    dashGap = 10.dp
                ),
                marker = {
                    Box(
                        modifier = Modifier
                            .size(24.dp)
                            .background(Color(0xFF2196F3), CircleShape)
                    )
                }
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
                lineType = getLineType(position, totalItems),
                orientation = TimelineOrientation.Horizontal,
                lineStyle = LineStyle.Normal(
                    color = Color(0xFF4CAF50),
                    width = 2.dp
                ),
                marker = {
                    Box(
                        modifier = Modifier
                            .size(24.dp)
                            .background(Color(0xFF2196F3), CircleShape)
                    )
                }
            )
        }
    }
}

@Preview(showBackground = true)
@Composable
private fun TimelineSingleItemPreview() {
    Column(Modifier.padding(16.dp)) {
        Timeline(
            lineType = LineType.START,
            lineStyle = LineStyle.Dashed(
                color = Color(0xFF2196F3),
                dashLength = 4.dp,
                dashGap = 2.dp
            ),
            marker = {
                Box(
                    modifier = Modifier
                        .size(24.dp)
                        .background(Color(0xFF2196F3), CircleShape)
                )
            }
        )
    }
}

@Preview(showBackground = true)
@Composable
private fun TimelineCustomMarkersPreview() {
    Column(Modifier.padding(16.dp)) {
        val totalItems = 4
        repeat(totalItems) { position ->
            Timeline(
                modifier = Modifier.height(100.dp),
                lineType = getLineType(position, totalItems),
                lineStyle = LineStyle.Dashed(
                    color = Color(0xFF2196F3),
                    dashLength = 8.dp,
                    dashGap = 4.dp
                )
            ) {
                // Custom marker examples
                when (position) {
                    0 -> Icon(
                        imageVector = Icons.Default.Star,
                        contentDescription = null,
                        tint = Color(0xFF2196F3),
                        modifier = Modifier.size(24.dp)
                    )

                    1 -> Box(
                        modifier = Modifier
                            .size(50.dp)
                            .background(Color(0xFF2196F3), RoundedCornerShape(4.dp))
                    )

                    2 -> Box(
                        Modifier
                            .size(24.dp)
                            .background(Color(0xFF2196F3), CircleShape)
                    ) {
                        Text(
                            modifier = Modifier.align(Alignment.Center),
                            text = "3",
                            color = Color.White,
                            textAlign = TextAlign.Center
                        )
                    }

                    else -> {
                        Box(
                            modifier = Modifier
                                .size(24.dp)
                                .background(Color(0xFF2196F3), CircleShape)
                        )
                    }
                }
            }
        }
    }
}