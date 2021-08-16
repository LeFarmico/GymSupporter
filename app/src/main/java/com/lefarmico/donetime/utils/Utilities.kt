package com.lefarmico.donetime.utils

import java.text.SimpleDateFormat
import java.util.Calendar
import java.util.Locale

object Utilities {
    fun getCurrentDateInFormat(): String {
        val date = Calendar.getInstance().time
        val format = SimpleDateFormat("dd.MM.yyy", Locale.getDefault())
        return format.format(date)
    }
}
