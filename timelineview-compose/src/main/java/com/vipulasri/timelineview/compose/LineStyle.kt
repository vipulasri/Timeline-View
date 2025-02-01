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

import androidx.compose.runtime.Immutable
import androidx.compose.runtime.Stable
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.Dp

/**
 * Represents the style configuration for timeline lines.
 * @property startLine The style for the line above the marker
 * @property endLine The style for the line below the marker
 */
@Immutable
data class LineStyle(
    val startLine: Line,
    val endLine: Line
) {
    companion object {
        /**
         * Creates a solid line style with same configuration for both start and end lines.
         * @param color The color of the lines
         * @param width The width/thickness of the lines
         */
        fun solid(
            color: Color = TimelineDefaults.LineColor,
            width: Dp = TimelineDefaults.LineWidth
        ) = LineStyle(
            startLine = SolidLine(color, width),
            endLine = SolidLine(color, width)
        )

        /**
         * Creates a dashed line style with same configuration for both start and end lines.
         * @param color The color of the lines
         * @param width The width/thickness of the lines
         * @param dashLength The length of each dash
         * @param dashGap The gap between dashes
         */
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

/**
 * Base class for timeline line styles.
 * @property color The color of the line
 * @property width The width/thickness of the line
 */
@Stable
abstract class Line internal constructor(
    open val color: Color,
    open val width: Dp,
)

/**
 * Represents a solid line style.
 */
@Immutable
data class SolidLine(
    override val color: Color = TimelineDefaults.LineColor,
    override val width: Dp = TimelineDefaults.LineWidth,
) : Line(color, width)

/**
 * Represents a dashed line style.
 * @property dashLength The length of each dash
 * @property dashGap The gap between dashes
 */
@Immutable
data class DashedLine(
    override val color: Color = TimelineDefaults.LineColor,
    override val width: Dp = TimelineDefaults.LineDashWidth,
    val dashLength: Dp = TimelineDefaults.LineDashLength,
    val dashGap: Dp = TimelineDefaults.LineDashGap,
) : Line(color, width)