package com.lefarmico.donetime.utils

import androidx.room.TypeConverter
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import com.lefarmico.donetime.data.entities.note.NoteExercise
import com.lefarmico.donetime.data.entities.note.NoteSet

class SetsTypeConverter {

    @TypeConverter
    fun fromSetsToString(sets: MutableList<NoteSet>): String {
        return Gson().toJson(sets)
    }

    @TypeConverter
    fun fromJsonToExercises(json: String): MutableList<NoteExercise> {
        val type = object : TypeToken<List<NoteSet>>() {}.type
        return Gson().fromJson(json, type)
    }
}
