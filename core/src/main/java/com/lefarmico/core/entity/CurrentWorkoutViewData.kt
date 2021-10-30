package com.lefarmico.core.entity

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

sealed class CurrentWorkoutViewData {

    data class Exercise(
        val id: Int = 0,
        val libraryId: Int,
        val title: String
    ) : CurrentWorkoutViewData()

    @Parcelize
    data class Set(
        val id: Int,
        val exerciseId: Int,
        val setNumber: Int,
        val weight: Float,
        val reps: Int
    ) : CurrentWorkoutViewData(), Parcelable

    data class ExerciseWithSets(
        val exercise: Exercise,
        val setList: List<Set> = mutableListOf()
    ) : CurrentWorkoutViewData()
}
