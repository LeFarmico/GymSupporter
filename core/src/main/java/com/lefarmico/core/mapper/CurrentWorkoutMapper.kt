package com.lefarmico.core.mapper

import com.lefarmico.core.entity.CurrentWorkoutViewData
import com.lefarmico.domain.entity.CurrentWorkoutDto
import com.lefarmico.domain.entity.WorkoutRecordsDto

/**
 * Converts the [CurrentWorkoutDto.ExerciseWithSets] to the [WorkoutRecordsDto.ExerciseWithSets]
 *  as a new instance.
 *
 *  Used as Dto entity
 */
@JvmName("ExerciseWithSetsDtoToRecordsDto")
fun List<CurrentWorkoutDto.ExerciseWithSets>.toRecordsDto() = this.map {
    WorkoutRecordsDto.ExerciseWithSets(
        exercise = WorkoutRecordsDto.Exercise(
            id = it.exercise.id,
            exerciseName = it.exercise.title,
            libraryId = it.exercise.libraryId
        ),
        setList = it.setList.map { set ->
            WorkoutRecordsDto.Set(
                setNumber = set.setNumber,
                weight = set.weight,
                reps = set.reps,
                measureType = WorkoutRecordsDto.MeasureType.KILO
            )
        }

    )
}

/**
 *  Converts the [CurrentWorkoutDto.Exercise] to the [CurrentWorkoutViewData.Exercise]
 *   as a new instance.
 *
 *  Used as ViewData entity
 */
fun CurrentWorkoutDto.Exercise.toViewData() = CurrentWorkoutViewData.Exercise(id, libraryId, title)

/**
 *  Converts the [CurrentWorkoutDto.Set] to the [CurrentWorkoutViewData.Set]
 *   as a new instance.
 *
 *  Used as ViewData entity
 */
fun CurrentWorkoutDto.Set.toViewData() = CurrentWorkoutViewData.Set(
    id = id, exerciseId = exerciseId, setNumber = setNumber, weight = weight, reps = reps
)

/**
 *  Converts a [List] of the [CurrentWorkoutDto.Set] to a [List] of the [CurrentWorkoutViewData.Set]
 *   as a new instance.
 *
 *  Used as ViewData entity
 */
@JvmName("CurrentWorkoutDtoSetToViewData")
fun List<CurrentWorkoutDto.Set>.toViewData() = this.map { it.toViewData() }

/**
 *  Converts the [CurrentWorkoutDto.Set] to the [CurrentWorkoutViewData.Set]
 *   as a new instance.
 *
 *  Used as ViewData entity
 */
fun CurrentWorkoutDto.ExerciseWithSets.toViewData() = CurrentWorkoutViewData.ExerciseWithSets(
    exercise = exercise.toViewData(),
    setList = setList.toViewData()
)

/**
 *  Converts a [List] of the [CurrentWorkoutDto.ExerciseWithSets] to a [List] of the [CurrentWorkoutViewData.ExerciseWithSets]
 *  as a new instance.
 *
 *  Used as ViewData entity
 */
@JvmName("CurrentWorkoutDtoExerciseWithSetsToViewData")
fun List<CurrentWorkoutDto.ExerciseWithSets>.toViewData() = this.map { it.toViewData() }
