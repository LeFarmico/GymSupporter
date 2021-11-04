package com.lefarmico.data.mapper

import com.lefarmico.data.db.entity.CurrentWorkoutData
import com.lefarmico.domain.entity.CurrentWorkoutDto

fun CurrentWorkoutDto.Exercise.toData() = CurrentWorkoutData.Exercise(id, libraryId, title)

fun CurrentWorkoutDto.Set.toData() = CurrentWorkoutData.Set(
    id = id, exerciseId = exerciseId, setNumber = setNumber, weight = weight, reps = reps
)

fun List<CurrentWorkoutDto.Set>.toDataSet() = this.map { it.toData() }

fun CurrentWorkoutDto.ExerciseWithSets.toData() = CurrentWorkoutData.ExerciseWithSets(
    exercise = exercise.toData(),
    setList = setList.toDataSet().toMutableList()
)
