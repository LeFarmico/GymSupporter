package com.lefarmico.core.mapper

import com.lefarmico.core.entity.WorkoutRecordsViewData
import com.lefarmico.domain.entity.WorkoutRecordsDto
import java.lang.IllegalArgumentException
import java.time.LocalDate
import java.time.format.DateTimeFormatter

/**
 *  Converts the [WorkoutRecordsDto.Exercise] to the [WorkoutRecordsViewData.Exercise]
 *   as a new instance.
 *
 *  Used as ViewData entity
 */
fun WorkoutRecordsDto.Exercise.toViewData() = WorkoutRecordsViewData.Exercise(
    id = id,
    exerciseName = exerciseName,
    workoutId = workoutId
)

/**
 *  Converts the [WorkoutRecordsDto.Set] to the [WorkoutRecordsViewData.Set]
 *   as a new instance.
 *
 *  Used as ViewData entity
 */
fun WorkoutRecordsDto.Set.toViewData() = WorkoutRecordsViewData.Set(
    id = id,
    exerciseId = exerciseId,
    setNumber = setNumber,
    weight = weight,
    reps = reps,
    measureType = measureType.toViewData()
)

/**
 *  Converts the [WorkoutRecordsDto.Workout] to the [WorkoutRecordsViewData.Workout]
 *   as a new instance.
 *
 *   field formatter is the [DateTimeFormatter] used for converting [LocalDate] to the [String] object.
 *
 *  Used as ViewData entity
 */
fun WorkoutRecordsDto.Workout.toViewData(
    dateFormatter: DateTimeFormatter,
    timeFormatter: DateTimeFormatter
) = WorkoutRecordsViewData.Workout(
    id = id,
    date = date.format(dateFormatter),
    time = time?.format(timeFormatter) ?: "",
    title = title
)

/**
 *  Converts a [List] of the [WorkoutRecordsDto.Set] to a [List] of the [WorkoutRecordsViewData.Set]
 *   as a new instance.
 *
 *  Used as ViewData entity
 */
@JvmName("WorkoutRecordsDtoSetToViewData")
fun List<WorkoutRecordsDto.Set>.toViewData() = this.map { it.toViewData() }

/**
 *  Converts a [List] of the [WorkoutRecordsDto.Exercise] to a [List] of the [WorkoutRecordsViewData.Exercise]
 *   as a new instance.
 *
 *  Used as ViewData entity
 */
@JvmName("WorkoutRecordsDtoExerciseToViewData")
fun List<WorkoutRecordsDto.Exercise>.toViewData() = this.map { it.toViewData() }

/**
 *  Converts a [List] of the [WorkoutRecordsDto.ExerciseWithSets] to a [List] of the [WorkoutRecordsViewData.ExerciseWithSets]
 *   as a new instance.
 *
 *  Used as ViewData entity
 */
@JvmName("viewDataExWithSets")
fun List<WorkoutRecordsDto.ExerciseWithSets>.toViewData() = this.map { it.toViewData() }

/**
 *  Converts a [List] of the [WorkoutRecordsDto.WorkoutWithExercisesAndSets] to a [List]
 *  of the [WorkoutRecordsViewData.WorkoutWithExercisesAndSets]
 *   as a new instance.
 *
 *  Used as ViewData entity
 */
@JvmName("viewDataWorkoutWithExAndSets")
fun List<WorkoutRecordsDto.WorkoutWithExercisesAndSets>.toViewData(
    formatter: DateTimeFormatter,
    timeFormatter: DateTimeFormatter
) = this.map { it.toViewData(formatter, timeFormatter) }

/**
 *  Converts the [WorkoutRecordsDto.MeasureType] to the [WorkoutRecordsViewData.MeasureType]
 *   as a new instance.
 *
 *  Used as ViewData entity
 */
fun WorkoutRecordsDto.MeasureType.toViewData(): WorkoutRecordsViewData.MeasureType {
    return when (this.typeNumber) {
        1 -> WorkoutRecordsViewData.MeasureType.KILO
        2 -> WorkoutRecordsViewData.MeasureType.LB
        else -> throw (IllegalArgumentException())
    }
}

/**
 *  Converts the [WorkoutRecordsDto.WorkoutWithExercisesAndSets]
 *  to the [WorkoutRecordsViewData.WorkoutWithExercisesAndSets]
 *   as a new instance.
 *
 *  Used as ViewData entity
 */
fun WorkoutRecordsDto.WorkoutWithExercisesAndSets.toViewData(
    dateFormatter: DateTimeFormatter,
    timeFormatter: DateTimeFormatter
) =
    WorkoutRecordsViewData.WorkoutWithExercisesAndSets(
        workout = workout.toViewData(dateFormatter, timeFormatter),
        exerciseWithSetsList = exerciseWithSetsList.toViewData()
    )

/**
 *  Converts the [WorkoutRecordsDto.ExerciseWithSets]
 *  to the [WorkoutRecordsViewData.ExerciseWithSets]
 *   as a new instance.
 *
 *  Used as ViewData entity
 */
fun WorkoutRecordsDto.ExerciseWithSets.toViewData() = WorkoutRecordsViewData.ExerciseWithSets(
    exercise = exercise.toViewData(),
    setList = setList.toViewData()
)
