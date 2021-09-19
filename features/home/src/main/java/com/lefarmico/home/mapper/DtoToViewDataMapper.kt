package com.lefarmico.home.mapper

import com.lefarmico.domain.entity.WorkoutRecordsDto
import com.lefarmico.home.viewData.WorkoutRecordViewData
import java.lang.IllegalArgumentException

fun WorkoutRecordsDto.Exercise.toViewData() = WorkoutRecordViewData.Exercise(
    exerciseName = exerciseName,
    noteSetList = noteSetList.toSetListData()
)

fun WorkoutRecordsDto.Set.toViewData() = WorkoutRecordViewData.Set(
    setNumber = setNumber,
    weight = weight,
    reps = reps,
    measureType = measureType.toViewData()
)

fun WorkoutRecordsDto.Workout.toViewData() = WorkoutRecordViewData.Workout(
    date = date,
    exerciseList = exerciseList.toExerciseListData()
)

fun List<WorkoutRecordsDto.Set>.toSetListData() = this.map { it.toViewData() }

fun List<WorkoutRecordsDto.Exercise>.toExerciseListData() = this.map { it.toViewData() }

fun List<WorkoutRecordsDto.Workout>.toWorkoutListData() = this.map { it.toViewData() }

fun WorkoutRecordsDto.MeasureType.toViewData(): WorkoutRecordViewData.MeasureType {
    return when (this.typeNumber) {
        1 -> WorkoutRecordViewData.MeasureType.KILO
        2 -> WorkoutRecordViewData.MeasureType.LB
        else -> throw (IllegalArgumentException())
    }
}
