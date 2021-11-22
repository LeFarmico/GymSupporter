package com.lefarmico.data.converter

import androidx.room.TypeConverter
import java.time.Instant
import java.time.LocalDateTime
import java.time.ZoneOffset
import java.util.*

class DateConverter {

    @TypeConverter
    fun fromTimestamp(value: Long): LocalDateTime {
        return value.let { LocalDateTime.ofInstant(Instant.ofEpochMilli(value), ZoneOffset.UTC) }
    }

    @TypeConverter
    fun dateToTimestamp(value: LocalDateTime): Long {
        return value.atZone(ZoneOffset.UTC).toInstant().toEpochMilli()
    }
}
