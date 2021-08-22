package com.lefarmico.donetime.utils

import androidx.room.TypeConverter
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import com.lefarmico.donetime.data.entities.note.NoteExercise

class ExercisesTypeConverter {

    @TypeConverter
    fun fromExercisesToString(exercises: MutableList<NoteExercise>): String {
        return Gson().toJson(exercises)
    }

    @TypeConverter
    fun fromJsonToExercises(json: String): MutableList<NoteExercise> {
        val type = object : TypeToken<List<NoteExercise>>() {}.type
        return Gson().fromJson(json, type)
    }
}
