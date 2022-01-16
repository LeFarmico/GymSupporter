package com.lefarmico.data.converter

import androidx.room.TypeConverter
import java.time.LocalTime

class TimeConverter {

    @TypeConverter
    fun fromTimestamp(value: Long?): LocalTime? {
        return value.let { it?.let { nanoTime -> LocalTime.ofNanoOfDay(nanoTime) } }
    }

    @TypeConverter
    fun timeToTimestamp(value: LocalTime?): Long? {
        return value.let { it?.toNanoOfDay() }
    }
}
