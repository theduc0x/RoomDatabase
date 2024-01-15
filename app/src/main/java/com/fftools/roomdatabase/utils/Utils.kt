package com.fftools.roomdatabase.utils

import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

object Utils {
    fun Long.convertSecondsToDateTime(): String {
        val dateFormat = SimpleDateFormat("yyyy-MM-dd", Locale.getDefault())
        val date = Date(this)
        return dateFormat.format(date)
    }
}