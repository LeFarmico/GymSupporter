package com.lefarmico.data.mapper

import com.lefarmico.data.db.entity.CurrentWorkoutData
import com.lefarmico.domain.entity.CurrentWorkoutDto

fun CurrentWorkoutData.Exercise.toDto() = CurrentWorkoutDto.Exercise(id, libraryId, title)

fun CurrentWorkoutData.Set.toDto() = CurrentWorkoutDto.Set(
    id = id, exerciseId = exerciseId, setNumber = setNumber, weight = weight, reps = reps
)

@JvmName("CurrentWorkoutDataSetToDto")
fun List<CurrentWorkoutData.Set>.toDto() = this.map { it.toDto() }

fun CurrentWorkoutData.ExerciseWithSets.toDto() = CurrentWorkoutDto.ExerciseWithSets(
    exercise = exercise.toDto(),
    setList = setList.toDto()
)

@JvmName("CurrentWorkoutDataExerciseWithSetsToDto")
fun List<CurrentWorkoutData.ExerciseWithSets>.toDto() = this.map { it.toDto() }
