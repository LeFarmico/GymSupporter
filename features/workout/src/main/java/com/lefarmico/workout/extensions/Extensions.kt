package com.lefarmico.workout.extensions

import com.lefarmico.core.entity.WorkoutRecordsViewData
import com.lefarmico.core.mapper.toRecordsDtoExWithSets
import com.lefarmico.core.utils.Utilities
import com.lefarmico.domain.entity.CurrentWorkoutDto
import com.lefarmico.domain.entity.WorkoutRecordsDto

fun CurrentWorkoutDto.ExerciseWithSets.toRecordsViewData() = WorkoutRecordsViewData.ExerciseWithSets(
    exercise = exercise.toRecordsViewData(),
    setList = setList.toSetViewData()
)

fun CurrentWorkoutDto.Exercise.toRecordsViewData() = WorkoutRecordsViewData.Exercise(
    id = id, exerciseName = title, workoutId = 0
)

fun CurrentWorkoutDto.Set.toRecordsViewData() = WorkoutRecordsViewData.Set(
    id = id,
    exerciseId = exerciseId,
    setNumber = setNumber,
    weight = weight,
    reps = reps,
    measureType = WorkoutRecordsViewData.MeasureType.KILO
)

fun List<CurrentWorkoutDto.Set>.toSetViewData() = this.map { it.toRecordsViewData() }

fun List<CurrentWorkoutDto.ExerciseWithSets>.toRecordsDto(): WorkoutRecordsDto.WorkoutWithExercisesAndSets {
    val workoutDto = WorkoutRecordsDto.Workout(date = Utilities.getCurrentDateInFormat())
    val exerciseWithSets = this.toRecordsDtoExWithSets()
    return WorkoutRecordsDto.WorkoutWithExercisesAndSets(workoutDto, exerciseWithSets)
}
