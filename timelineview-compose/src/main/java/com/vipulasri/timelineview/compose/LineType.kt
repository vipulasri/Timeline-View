package com.vipulasri.timelineview.compose

enum class LineType {
    START,    // First item
    MIDDLE,   // Middle items
    END,      // Last item
    SINGLE    // Single item
}

fun getLineType(position: Int, totalItems: Int): LineType {
    return when {
        totalItems == 1 -> LineType.SINGLE
        position == 0 -> LineType.START
        position == totalItems - 1 -> LineType.END
        else -> LineType.MIDDLE
    }
}