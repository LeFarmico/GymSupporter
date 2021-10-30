package com.lefarmico.core.mapper

import com.lefarmico.core.entity.CurrentWorkoutViewData
import com.lefarmico.domain.entity.CurrentWorkoutDto

fun CurrentWorkoutViewData.Exercise.toDto() = CurrentWorkoutDto.Exercise.Builder()
    .setLibraryId(libraryId)
    .setId(id)
    .setTitle(title)
    .build()

fun CurrentWorkoutViewData.Set.toDto() = CurrentWorkoutDto.Set(
    id = id, exerciseId = exerciseId, setNumber = setNumber, weight = weight, reps = reps
)

fun List<CurrentWorkoutViewData.Set>.toDtoSet() = this.map { it.toDto() }

fun CurrentWorkoutViewData.ExerciseWithSets.toDto() = CurrentWorkoutDto.ExerciseWithSets(
    exercise = exercise.toDto(),
    setList = setList.toDtoSet()
)

fun List<CurrentWorkoutViewData.ExerciseWithSets>.toDtoExWithSets() = this.map { it.toDto() }
