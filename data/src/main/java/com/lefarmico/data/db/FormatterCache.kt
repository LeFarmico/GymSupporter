package com.lefarmico.data.db

import androidx.annotation.IntDef
import com.lefarmico.data.db.entity.FormatterData
import java.time.format.DateTimeFormatter

class FormatterCache {

    private val formatterSet = mutableSetOf<FormatterData>()

    init {
        formatterSet.add(FormatterData(DD_MM_YYYY_DOTS, DateTimeFormatter.ofPattern("dd.MM.yyyy")))
        formatterSet.add(FormatterData(MM_DD_YYYY_DOTS, DateTimeFormatter.ofPattern("MM.dd.yyyy")))
        formatterSet.add(FormatterData(YYYY_MM_DD_DOTS, DateTimeFormatter.ofPattern("yyyy.MM.dd")))

        formatterSet.add(FormatterData(DD_MM_YYYY_SLASH, DateTimeFormatter.ofPattern("dd/MM/yyyy")))
        formatterSet.add(FormatterData(MM_DD_YYYY_SLASH, DateTimeFormatter.ofPattern("MM/dd/yyyy")))
        formatterSet.add(FormatterData(YYYY_MM_DD_SLASH, DateTimeFormatter.ofPattern("yyyy/MM/dd")))

        formatterSet.add(FormatterData(DD_MM_YYYY_DASH, DateTimeFormatter.ofPattern("dd-MM-yyyy")))
        formatterSet.add(FormatterData(MM_DD_YYYY_DASH, DateTimeFormatter.ofPattern("MM-dd-yyyy")))
        formatterSet.add(FormatterData(YYYY_MM_DD_DASH, DateTimeFormatter.ofPattern("yyyy-MM-dd")))
    }

    fun getFormatterById(@FormatterId formatterId: Int): FormatterData {
        return formatterSet.find { it.id == formatterId }
            ?: throw IllegalArgumentException("that formatterId is not exist")
    }

    fun getFormattersDataList(): List<FormatterData> {
        return formatterSet.toList()
    }

    companion object {

        @IntDef(
            DD_MM_YYYY_DOTS, MM_DD_YYYY_DOTS, YYYY_MM_DD_DOTS,
            DD_MM_YYYY_SLASH, MM_DD_YYYY_SLASH, YYYY_MM_DD_SLASH,
            DD_MM_YYYY_DASH, MM_DD_YYYY_DASH, YYYY_MM_DD_DASH
        )
        @Retention(AnnotationRetention.SOURCE)
        annotation class FormatterId

        const val DD_MM_YYYY_DOTS = 0
        const val MM_DD_YYYY_DOTS = 1
        const val YYYY_MM_DD_DOTS = 2

        const val DD_MM_YYYY_SLASH = 3
        const val MM_DD_YYYY_SLASH = 4
        const val YYYY_MM_DD_SLASH = 5

        const val DD_MM_YYYY_DASH = 6
        const val MM_DD_YYYY_DASH = 7
        const val YYYY_MM_DD_DASH = 8
    }
}
