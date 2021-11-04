package com.lefarmico.domain.entity

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

sealed class CurrentWorkoutDto {

    data class Exercise(
        val id: Int = 0,
        val libraryId: Int,
        val title: String
    ) : CurrentWorkoutDto()

    @Parcelize
    data class Set(
        val id: Int,
        val exerciseId: Int,
        val setNumber: Int,
        val weight: Float,
        val reps: Int
    ) : CurrentWorkoutDto(), Parcelable

    data class ExerciseWithSets(
        val exercise: Exercise,
        val setList: List<Set> = mutableListOf()
    ) : CurrentWorkoutDto()
}
