package com.lefarmico.data.db

import androidx.annotation.IntDef
import com.lefarmico.data.db.entity.FormatterData
import java.time.format.DateTimeFormatter

class FormatterTimeCache {

    private val lock = Any()
    private val formatterSet = mutableSetOf<FormatterData>()

    init {
        formatterSet.add(FormatterData(H_24, DateTimeFormatter.ofPattern("hh:mm")))
        formatterSet.add(FormatterData(H_12, DateTimeFormatter.ofPattern("hh:mm a")))
    }

    fun getFormatterById(@FormatterTimeId formatterId: Int): FormatterData {
        synchronized(lock) {
            return formatterSet.find { it.id == formatterId }
                ?: throw IllegalArgumentException("that formatterId is not exist")
        }
    }

    fun getFormattersDataList(): List<FormatterData> {
        synchronized(lock) {
            return formatterSet.toList()
        }
    }

    companion object {

        @IntDef(H_24, H_12)
        @Retention(AnnotationRetention.SOURCE)
        annotation class FormatterTimeId

        const val H_24 = 0
        const val H_12 = 1
    }
}
