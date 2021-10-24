package com.lefarmico.data.mapper

import com.lefarmico.data.db.entity.CurrentWorkoutData
import com.lefarmico.domain.entity.CurrentWorkoutDto

fun CurrentWorkoutDto.Exercise.toData() = CurrentWorkoutData.Exercise.Builder()
    .setId(id)
    .setLibraryId(libraryId)
    .setTitle(title)
    .build()

fun CurrentWorkoutDto.Set.toData() = CurrentWorkoutData.Set(
    id = id, exerciseId = exerciseId, setNumber = setNumber, weight = weight, reps = reps
)

fun List<CurrentWorkoutDto.Set>.toCurrentWorkoutData() = this.map { it.toData() }

fun CurrentWorkoutDto.ExerciseWithSets.toData() = CurrentWorkoutData.ExerciseWithSets(
    exercise = exercise.toData(),
    setList = setList.toCurrentWorkoutData().toMutableList()
)
