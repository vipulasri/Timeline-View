package com.github.vipulasri.timelineview.sample.utils

import android.content.Context
import androidx.core.content.ContextCompat
import com.github.vipulasri.timelineview.sample.R

object ColorUtils {
    fun getColorsFromResource(context: Context): List<Int> {
        val typedArray = context.resources.obtainTypedArray(R.array.colors)
        val colorList = mutableListOf<Int>()

        for (i in 0 until typedArray.length()) {
            val colorResId = typedArray.getResourceId(i, 0)
            if (colorResId != 0) {
                val color = ContextCompat.getColor(context, colorResId)
                colorList.add(color)
            }
        }
        typedArray.recycle()
        return colorList
    }

   /* fun getColorFromResource(context: Context, colorResId: Int): Int {
        return ContextCompat.getColor(context, colorResId)
    }*/
}