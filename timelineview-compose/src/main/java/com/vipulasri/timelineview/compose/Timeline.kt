/*
 * Copyright 2025 Vipul Asri
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.vipulasri.timelineview.compose

import androidx.compose.foundation.layout.Box
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.drawWithCache
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.PathEffect
import androidx.compose.ui.graphics.drawscope.DrawScope
import androidx.compose.ui.unit.Dp

/**
 * A composable that displays a timeline marker with connecting lines.
 *
 * @param modifier The modifier to be applied to the timeline
 * @param lineType The type of line connections (start, middle, end, or single)
 * @param lineStyle The style configuration for the lines
 * @param orientation The orientation of the timeline (vertical or horizontal)
 * @param marker The composable to be used as the timeline marker
 *
 * Example usage:
 * ```
 * Timeline(
 *     lineType = LineType.MIDDLE,
 *     lineStyle = LineStyle.dashed(
 *         color = MaterialTheme.colorScheme.primary,
 *         width = 2.dp
 *     ),
 *     marker = {
 *         // Your marker composable
 *     }
 * )
 * ```
 */
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
    val line = lineStyle.startLine
    val startOffset = when {
        line is DashedLine -> {
            Offset(this.center.x, 0f)
        }

        else -> {
            // For solid line, draw normally
            Offset(this.center.x, 0f)
        }
    }

    val endOffset = when {
        line is DashedLine -> {
            this.center - Offset(0f, markerSize.div(2).toPx())
        }

        else -> {
            // For solid line, end at marker
            this.center - Offset(0f, markerSize.div(2).toPx())
        }
    }

    drawLine(
        color = line.color,
        start = startOffset,
        end = endOffset,
        strokeWidth = line.width.toPx(),
        pathEffect = getPathEffect(line)
    )
}

private fun DrawScope.drawEndLine(markerSize: Dp, lineStyle: LineStyle) {
    val line = lineStyle.endLine
    val startOffset = when {
        line is DashedLine -> {
            // For dashed line, start from bottom minus gap
            Offset(this.center.x, size.height - (line.dashGap.toPx()))
        }

        else -> {
            // For solid line, start from marker
            this.center + Offset(0f, markerSize.div(2).toPx())
        }
    }
    val endOffset = when {
        line is DashedLine -> {
            // For dashed line, draw towards marker
            this.center + Offset(0f, markerSize.div(2).toPx())
        }

        else -> {
            // For solid line, draw to bottom
            Offset(this.center.x, size.height)
        }
    }

    drawLine(
        color = line.color,
        start = startOffset,
        end = endOffset,
        strokeWidth = line.width.toPx(),
        pathEffect = getPathEffect(line)
    )
}

private fun DrawScope.drawStartLineHorizontal(markerSize: Dp, lineStyle: LineStyle) {
    val line = lineStyle.startLine
    val startOffset = when {
        line is DashedLine -> {
            // For dashed line, start from left edge plus gap
            Offset(0f, this.center.y)
        }

        else -> {
            // For solid line, start from left edge
            Offset(0f, this.center.y)
        }
    }
    val endOffset = when {
        line is DashedLine -> {
            // For dashed line, draw towards marker
            this.center - Offset(markerSize.div(2).toPx(), 0f)
        }

        else -> {
            // For solid line, end at marker
            this.center - Offset(markerSize.div(2).toPx(), 0f)
        }
    }

    drawLine(
        color = line.color,
        start = startOffset,
        end = endOffset,
        strokeWidth = line.width.toPx(),
        pathEffect = getPathEffect(line)
    )
}

private fun DrawScope.drawEndLineHorizontal(markerSize: Dp, lineStyle: LineStyle) {
    val line = lineStyle.endLine
    val startOffset = when {
        line is DashedLine -> {
            // For dashed line, start from right edge minus gap
            Offset(size.width - (line.dashGap.toPx()), this.center.y)
        }

        else -> {
            // For solid line, start from marker
            this.center + Offset(markerSize.div(2).toPx(), 0f)
        }
    }
    val endOffset = when {
        line is DashedLine -> {
            // For dashed line, draw towards marker
            this.center + Offset(markerSize.div(2).toPx(), 0f)
        }

        else -> {
            // For solid line, draw to right edge
            Offset(size.width, this.center.y)
        }
    }

    drawLine(
        color = line.color,
        start = startOffset,
        end = endOffset,
        strokeWidth = line.width.toPx(),
        pathEffect = getPathEffect(line)
    )
}

private fun DrawScope.getPathEffect(line: Line): PathEffect? = when (line) {
    is DashedLine -> PathEffect.dashPathEffect(
        floatArrayOf(line.dashLength.toPx(), line.dashGap.toPx())
    )
    else -> null
}