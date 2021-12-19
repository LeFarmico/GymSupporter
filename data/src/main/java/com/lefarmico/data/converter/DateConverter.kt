package com.lefarmico.data.converter

import androidx.room.TypeConverter
import java.time.LocalDate

class DateConverter {

    @TypeConverter
    fun fromTimestamp(value: Long): LocalDate {
        return value.let { LocalDate.ofEpochDay(value) }
    }

    @TypeConverter
    fun dateToTimestamp(value: LocalDate): Long {
        return value.let { value.toEpochDay() }
    }
}
