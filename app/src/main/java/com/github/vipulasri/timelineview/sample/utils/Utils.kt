package com.github.vipulasri.timelineview.sample.utils

import android.content.Context
import android.content.res.Resources
import android.util.TypedValue

object Utils {

    @Deprecated("Use extension function instead.")
    fun dpToPx(dp: Float, context: Context): Int {
        return dpToPx(dp, context.resources)
    }

    @Deprecated("Use extension function instead.")
    private fun dpToPx(dp: Float, resources: Resources): Int {
        val px = TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, dp, resources.displayMetrics)
        return px.toInt()
    }
}
