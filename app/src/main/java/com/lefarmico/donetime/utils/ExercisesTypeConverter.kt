package com.lefarmico.donetime.utils

import androidx.room.TypeConverter
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import com.lefarmico.donetime.data.entities.note.ExerciseNote

class ExercisesTypeConverter {

    @TypeConverter
    fun fromExercisesToString(exercises: MutableList<ExerciseNote>): String {
        return Gson().toJson(exercises)
    }

    @TypeConverter
    fun fromJsonToExercises(json: String): MutableList<ExerciseNote> {
        val type = object : TypeToken<List<ExerciseNote>>() {}.type
        return Gson().fromJson(json, type)
    }
}
