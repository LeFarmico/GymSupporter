package com.lefarmico.donetime.utils

import androidx.room.TypeConverter
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import com.lefarmico.donetime.data.entities.exercise.ExerciseData
import com.lefarmico.donetime.data.entities.exercise.IExerciseData

class ExercisesTypeConverter {

    @TypeConverter
    fun fromExercisesToString(exercises: List<IExerciseData>): String {
        return Gson().toJson(exercises)
    }

    @TypeConverter
    fun fromJsonToExercises(json: String): List<IExerciseData> {
        val type = object : TypeToken<List<ExerciseData>>() {}.type
        return Gson().fromJson(json, type)
    }
}
