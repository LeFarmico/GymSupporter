package com.lefarmico.workout.extensions

import com.lefarmico.core.entity.WorkoutRecordsViewData
import com.lefarmico.domain.entity.CurrentWorkoutDto
import com.lefarmico.domain.entity.WorkoutRecordsDto
import java.lang.IllegalArgumentException

fun CurrentWorkoutDto.Set.toRecordsDto() = WorkoutRecordsDto.Set(
    id = this.exerciseId,
    exerciseId = this.exerciseId,
    setNumber = this.setNumber,
    weight = this.weight,
    reps = this.reps,
    measureType = this.measureType.toRecordsDto()
)

fun CurrentWorkoutDto.MeasureType.toRecordsDto(): WorkoutRecordsDto.MeasureType {
    return when (this.typeNumber) {
        1 -> WorkoutRecordsDto.MeasureType.KILO
        2 -> WorkoutRecordsDto.MeasureType.LB
        else -> throw (IllegalArgumentException())
    }
}
// TODO: костыль
fun CurrentWorkoutDto.Exercise.toViewData() = WorkoutRecordsViewData.Exercise(id, exerciseName, 0)

fun CurrentWorkoutDto.Set.toViewData() = WorkoutRecordsViewData.Set(
    id = id,
    exerciseId = exerciseId,
    setNumber = setNumber,
    weight = weight,
    reps = reps,
    measureType = measureType.toViewData()
)

fun CurrentWorkoutDto.MeasureType.toViewData(): WorkoutRecordsViewData.MeasureType {
    return when (this.typeNumber) {
        1 -> WorkoutRecordsViewData.MeasureType.KILO
        2 -> WorkoutRecordsViewData.MeasureType.LB
        else -> throw (IllegalArgumentException())
    }
}

fun CurrentWorkoutDto.ExerciseWithSets.toViewData() = WorkoutRecordsViewData.ExerciseWithSets(
    exercise = exercise.toViewData(),
    setList = setList.toSetViewData()
)
fun List<CurrentWorkoutDto.ExerciseWithSets>.toExerciseWithSetsViewData() = this.map { it.toViewData() }

fun List<CurrentWorkoutDto.Set>.toSetViewData() = this.map { it.toViewData() }
