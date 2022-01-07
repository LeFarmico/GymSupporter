package com.lefarmico.core.mapper

import com.lefarmico.core.entity.WorkoutRecordsViewData
import com.lefarmico.domain.entity.WorkoutRecordsDto
import java.lang.IllegalArgumentException
import java.time.format.DateTimeFormatter

fun WorkoutRecordsDto.Exercise.toViewData() = WorkoutRecordsViewData.Exercise(
    id = id,
    exerciseName = exerciseName,
    workoutId = workoutId
)

fun WorkoutRecordsDto.Set.toViewData() = WorkoutRecordsViewData.Set(
    id = id,
    exerciseId = exerciseId,
    setNumber = setNumber,
    weight = weight,
    reps = reps,
    measureType = measureType.toViewData()
)

fun WorkoutRecordsDto.Workout.toViewData(formatter: DateTimeFormatter) = WorkoutRecordsViewData.Workout(
    id = id,
    date = date.format(formatter),
    title = title
)

fun List<WorkoutRecordsDto.Set>.toViewDataSet() = this.map { it.toViewData() }

fun List<WorkoutRecordsDto.Exercise>.toViewData() = this.map { it.toViewData() }

@JvmName("viewDataExWithSets")
fun List<WorkoutRecordsDto.ExerciseWithSets>.toViewData() = this.map { it.toViewData() }

@JvmName("viewDataWorkoutWithExAndSets")
fun List<WorkoutRecordsDto.WorkoutWithExercisesAndSets>.toViewData(formatter: DateTimeFormatter) =
    this.map { it.toViewData(formatter) }

fun WorkoutRecordsDto.MeasureType.toViewData(): WorkoutRecordsViewData.MeasureType {
    return when (this.typeNumber) {
        1 -> WorkoutRecordsViewData.MeasureType.KILO
        2 -> WorkoutRecordsViewData.MeasureType.LB
        else -> throw (IllegalArgumentException())
    }
}

fun WorkoutRecordsDto.WorkoutWithExercisesAndSets.toViewData(formatter: DateTimeFormatter) =
    WorkoutRecordsViewData.WorkoutWithExercisesAndSets(
        workout = workout.toViewData(formatter),
        exerciseWithSetsList = exerciseWithSetsList.toViewData()
    )

fun WorkoutRecordsDto.ExerciseWithSets.toViewData() = WorkoutRecordsViewData.ExerciseWithSets(
    exercise = exercise.toViewData(),
    setList = setList.toViewDataSet()
)
