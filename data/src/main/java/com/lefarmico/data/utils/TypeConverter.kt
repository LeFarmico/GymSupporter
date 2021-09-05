package com.lefarmico.data.utils

import androidx.room.TypeConverter
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import com.lefarmico.data.db.entity.WorkoutRecordsData

class TypeConverter {

    class ExerciseList {

        @TypeConverter
        fun fromExercisesToString(exercises: List<WorkoutRecordsData.Exercise>): String {
            return Gson().toJson(exercises)
        }

        @TypeConverter
        fun fromJsonToExercises(json: String): MutableList<WorkoutRecordsData.Exercise> {
            val type = object : TypeToken<List<WorkoutRecordsData.Exercise>>() {}.type
            return Gson().fromJson(json, type)
        }
    }

    class SetsList {

        @TypeConverter
        fun fromSetsToString(sets: List<WorkoutRecordsData.Set>): String {
            return Gson().toJson(sets)
        }

        @TypeConverter
        fun fromJsonToExercises(json: String): MutableList<WorkoutRecordsData.Set> {
            val type = object : TypeToken<List<WorkoutRecordsData.Set>>() {}.type
            return Gson().fromJson(json, type)
        }
    }
}
