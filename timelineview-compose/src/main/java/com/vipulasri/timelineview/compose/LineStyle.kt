package com.vipulasri.timelineview.compose

import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.Dp

sealed class LineStyle {
    abstract fun color(): Color
    abstract fun width(): Dp

    data class Normal(
        private val color: Color = TimelineDefaults.LineColor,
        private val width: Dp = TimelineDefaults.LineWidth
    ) : LineStyle() {
        override fun color() = color
        override fun width() = width
    }

    data class Dashed(
        private val color: Color = TimelineDefaults.LineColor,
        private val width: Dp = TimelineDefaults.LineWidth,
        val dashLength: Dp = TimelineDefaults.LineDashLength,
        val dashGap: Dp = TimelineDefaults.LineDashGap
    ) : LineStyle() {
        override fun color() = color
        override fun width() = width
    }
} 