package com.lefarmico.core.mapper

import com.lefarmico.core.entity.CurrentWorkoutViewData
import com.lefarmico.domain.entity.CurrentWorkoutDto

fun CurrentWorkoutDto.Exercise.toViewData() = CurrentWorkoutViewData.Exercise(id, libraryId, title)

fun CurrentWorkoutDto.Set.toViewData() = CurrentWorkoutViewData.Set(
    id = id, exerciseId = exerciseId, setNumber = setNumber, weight = weight, reps = reps
)

fun List<CurrentWorkoutDto.Set>.toViewDataSet() = this.map { it.toViewData() }

fun CurrentWorkoutDto.ExerciseWithSets.toViewData() = CurrentWorkoutViewData.ExerciseWithSets(
    exercise = exercise.toViewData(),
    setList = setList.toViewDataSet().toMutableList()
)

fun List<CurrentWorkoutDto.ExerciseWithSets>.toViewData() = this.map { it.toViewData() }
