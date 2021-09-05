package com.lefarmico.data.utils

import com.lefarmico.data.db.entity.WorkoutRecordsData
import com.lefarmico.domain.entity.WorkoutRecordsDto
import java.lang.IllegalArgumentException

fun WorkoutRecordsDto.Exercise.toData() = WorkoutRecordsData.Exercise(
    id = id,
    exerciseName = exerciseName,
    noteSetList = noteSetList.toSetListData()
)

fun WorkoutRecordsDto.Set.toData() = WorkoutRecordsData.Set(
    id = id,
    exerciseId = exerciseId,
    setNumber = setNumber,
    weight = weight,
    reps = reps,
    measureType = measureType.toData()
)

fun WorkoutRecordsDto.Workout.toData() = WorkoutRecordsData.Workout(
    id = id,
    date = date,
    exerciseList = exerciseList.toExerciseListData()
)

fun List<WorkoutRecordsDto.Set>.toSetListData() = this.map { it.toData() }

fun List<WorkoutRecordsDto.Exercise>.toExerciseListData() = this.map { it.toData() }

fun List<WorkoutRecordsDto.Workout>.toWorkoutListData() = this.map { it.toData() }

fun WorkoutRecordsDto.MeasureType.toData(): WorkoutRecordsData.MeasureType {
    return when (this.typeNumber) {
        1 -> WorkoutRecordsData.MeasureType.KILO
        2 -> WorkoutRecordsData.MeasureType.LB
        else -> throw (IllegalArgumentException())
    }
}
