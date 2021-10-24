package com.lefarmico.data.mapper

import com.lefarmico.data.db.entity.CurrentWorkoutData
import com.lefarmico.domain.entity.CurrentWorkoutDto

fun CurrentWorkoutData.Exercise.toDto() = CurrentWorkoutDto.Exercise.Builder()
    .setId(id)
    .setTitle(title)
    .setLibraryId(libraryId)
    .build()

fun CurrentWorkoutData.Set.toDto() = CurrentWorkoutDto.Set(
    id = id, exerciseId = exerciseId, setNumber = setNumber, weight = weight, reps = reps
)

fun List<CurrentWorkoutData.Set>.toSetDto() = this.map { it.toDto() }

fun CurrentWorkoutData.ExerciseWithSets.toDto() = CurrentWorkoutDto.ExerciseWithSets(
    exercise = exercise.toDto(),
    setList = setList.toSetDto()
)

fun List<CurrentWorkoutData.ExerciseWithSets>.toExerciseWithSetsDto() = this.map { it.toDto() }
