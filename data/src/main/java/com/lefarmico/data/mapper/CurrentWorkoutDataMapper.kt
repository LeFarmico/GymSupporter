package com.lefarmico.data.mapper

import com.lefarmico.data.db.entity.CurrentWorkoutData
import com.lefarmico.domain.entity.CurrentWorkoutDto

fun CurrentWorkoutDto.Exercise.toData() = CurrentWorkoutData.Exercise(id, exerciseName)

fun CurrentWorkoutDto.Set.toData() = CurrentWorkoutData.Set(
    id = id,
    exerciseId = exerciseId,
    setNumber = setNumber,
    weight = weight,
    reps = reps,
    measureType = measureType.toData()
)

fun CurrentWorkoutDto.MeasureType.toData(): CurrentWorkoutData.MeasureType {
    return when (this.typeNumber) {
        1 -> CurrentWorkoutData.MeasureType.KILO
        2 -> CurrentWorkoutData.MeasureType.LB
        else -> throw (IllegalArgumentException())
    }
}

fun CurrentWorkoutDto.ExerciseWithSets.toData() = CurrentWorkoutData.ExerciseWithSets(
    exercise = exercise.toData(),
    setList = setList.toSetData()
)
fun List<CurrentWorkoutDto.ExerciseWithSets>.toExerciseWithSetsData() = this.map { it.toData() }

fun List<CurrentWorkoutDto.Set>.toSetData() = this.map { it.toData() }