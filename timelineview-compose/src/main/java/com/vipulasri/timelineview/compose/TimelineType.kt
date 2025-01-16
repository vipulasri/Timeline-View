package com.vipulasri.timelineview.compose

enum class TimelineType {
    START,    // First item
    MIDDLE,   // Middle items
    END,      // Last item
    SINGLE    // Single item
}

fun getTimelineType(position: Int, totalItems: Int): TimelineType {
    return when {
        totalItems == 1 -> TimelineType.SINGLE
        position == 0 -> TimelineType.START
        position == totalItems - 1 -> TimelineType.END
        else -> TimelineType.MIDDLE
    }
}