package com.lefarmico.data.mapper

import com.lefarmico.data.db.entity.CurrentWorkoutData
import com.lefarmico.domain.entity.CurrentWorkoutDto

fun CurrentWorkoutData.Exercise.toDto() = CurrentWorkoutDto.Exercise(id, exerciseName)

fun CurrentWorkoutData.Set.toDto() = CurrentWorkoutDto.Set(
    id = id,
    exerciseId = exerciseId,
    setNumber = setNumber,
    weight = weight,
    reps = reps,
    measureType = measureType.toDto()
)

fun CurrentWorkoutData.MeasureType.toDto(): CurrentWorkoutDto.MeasureType {
    return when (this.typeNumber) {
        1 -> CurrentWorkoutDto.MeasureType.KILO
        2 -> CurrentWorkoutDto.MeasureType.LB
        else -> throw (IllegalArgumentException())
    }
}

fun CurrentWorkoutData.ExerciseWithSets.toDto() = CurrentWorkoutDto.ExerciseWithSets(
    exercise = exercise.toDto(),
    setList = setList.toSetDto()
)
fun List<CurrentWorkoutData.ExerciseWithSets>.toExerciseWithSetsDto() = this.map { it.toDto() }

fun List<CurrentWorkoutData.Set>.toSetDto() = this.map { it.toDto() }