package com.github.vipulasri.timelineview.sample.utils

import android.text.format.DateUtils

import java.text.ParseException
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

/**
 * Created by Vipul Asri on 19-07-2016.
 */

object DateTimeUtils {

    fun parseDateTime(dateString: String, originalFormat: String, outputFromat: String): String {

        val formatter = SimpleDateFormat(originalFormat, Locale.US)
        var date: Date? = null
        return try {
            date = formatter.parse(dateString)

            val dateFormat = SimpleDateFormat(outputFromat, Locale("US"))

            dateFormat.format(date)

        } catch (e: ParseException) {
            e.printStackTrace().toString()
        }

    }

    fun getRelativeTimeSpan(dateString: String, originalFormat: String): String {

        val formatter = SimpleDateFormat(originalFormat, Locale.US)
        var date: Date? = null
        return try {
            date = formatter.parse(dateString)

            DateUtils.getRelativeTimeSpanString(date!!.time).toString()

        } catch (e: ParseException) {
            e.printStackTrace()
            ""
        }

    }
}
