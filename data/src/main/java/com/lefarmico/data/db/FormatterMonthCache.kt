package com.lefarmico.data.db

import androidx.annotation.IntDef
import com.lefarmico.data.db.entity.FormatterData
import java.time.format.DateTimeFormatter
import java.util.*

class FormatterMonthCache {

    private val lock = Any()
    private val formatterMonthSet = mutableSetOf<FormatterData>()

    init {
        formatterMonthSet.add(FormatterData(MMMM_YYYY_SPACE, DateTimeFormatter.ofPattern("MMMM yyyy", Locale.getDefault())))
        formatterMonthSet.add(FormatterData(MM_YYYY_DOTS, DateTimeFormatter.ofPattern("MM.yyyy", Locale.getDefault())))
        formatterMonthSet.add(FormatterData(MM_YYYY_SLASH, DateTimeFormatter.ofPattern("MM/yyyy", Locale.getDefault())))
        formatterMonthSet.add(FormatterData(MM_YYYY_DASH, DateTimeFormatter.ofPattern("MM-yyyy", Locale.getDefault())))
    }

    fun getFormatterById(@FormatterMonthId formatterId: Int): FormatterData {
        synchronized(lock) {
            return formatterMonthSet.find { it.id == formatterId }
                ?: throw NullPointerException("that formatterId is not exist")
        }
    }

    fun getFormattersDataList(): List<FormatterData> {
        synchronized(lock) {
            return formatterMonthSet.toList()
        }
    }

    companion object {

        @IntDef(
            MMMM_YYYY_DOTS, MMMM_YYYY_SPACE,
            YYYY_MMMM_DOTS, YYYY_MMMM_SPACE,
            MM_YYYY_DASH, MM_YYYY_DOTS, MM_YYYY_SLASH,
            YYYY_MM_DOTS, YYYY_MM_DASH, YYYY_MM_SLASH
        )
        @Retention(AnnotationRetention.SOURCE)
        annotation class FormatterMonthId

        const val MMMM_YYYY_SPACE = 0
        const val MMMM_YYYY_DOTS = 1

        const val YYYY_MMMM_SPACE = 2
        const val YYYY_MMMM_DOTS = 3

        const val MM_YYYY_DOTS = 4
        const val MM_YYYY_SLASH = 5
        const val MM_YYYY_DASH = 6

        const val YYYY_MM_DOTS = 7
        const val YYYY_MM_SLASH = 8
        const val YYYY_MM_DASH = 9
    }
}
