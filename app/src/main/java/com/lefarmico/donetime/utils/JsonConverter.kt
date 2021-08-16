package com.lefarmico.donetime.utils

import com.google.gson.Gson
import com.google.gson.GsonBuilder
import com.lefarmico.donetime.data.entities.json.WorkoutEntityImpl
import com.lefarmico.donetime.data.entities.workout.IWorkoutEntity

object JsonConverter {

    fun fromWorkoutDataToJson(workoutEntity: IWorkoutEntity): String {
        val gson = GsonBuilder().excludeFieldsWithoutExposeAnnotation().create()
        val entity = WorkoutEntityImpl(workoutEntity)
        return gson.toJson(entity)
    }

    fun fromJsonToWorkoutData(json: String): WorkoutEntityImpl {
        return Gson().fromJson(json, WorkoutEntityImpl::class.java)
    }
}
