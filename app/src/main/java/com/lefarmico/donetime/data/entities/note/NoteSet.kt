package com.lefarmico.donetime.data.entities.note

import androidx.room.TypeConverters
import com.lefarmico.donetime.utils.SetsTypeConverter

@TypeConverters(SetsTypeConverter::class)
data class NoteSet(
    val setNumber: Int,
    val weight: Float,
    val reps: Int
)
