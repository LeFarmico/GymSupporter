package com.lefarmico.workout.extensions

import com.lefarmico.core.entity.WorkoutRecordsViewData
import com.lefarmico.core.mapper.toRecords
import com.lefarmico.core.utils.Utilities
import com.lefarmico.domain.entity.CurrentWorkoutDto
import com.lefarmico.domain.entity.WorkoutRecordsDto

fun CurrentWorkoutDto.ExerciseWithSets.toViewData() = WorkoutRecordsViewData.ExerciseWithSets(
    exercise = exercise.toViewData(),
    setList = setList.toSetViewData()
)

fun CurrentWorkoutDto.Exercise.toViewData() = WorkoutRecordsViewData.Exercise(
    id = id, exerciseName = title, workoutId = 0
)

fun CurrentWorkoutDto.Set.toViewData() = WorkoutRecordsViewData.Set(
    id = id,
    exerciseId = exerciseId,
    setNumber = setNumber,
    weight = weight,
    reps = reps,
    measureType = WorkoutRecordsViewData.MeasureType.KILO
)

fun List<CurrentWorkoutDto.Set>.toSetViewData() = this.map { it.toViewData() }

fun List<CurrentWorkoutDto.ExerciseWithSets>.toExerciseViewData() = this.map { it.toViewData() }

fun List<CurrentWorkoutDto.ExerciseWithSets>.toRecordsDto(): WorkoutRecordsDto.WorkoutWithExercisesAndSets {
    val workoutDto = WorkoutRecordsDto.Workout(date = Utilities.getCurrentDateInFormat())
    val exerciseWithSets = this.toRecords()
    return WorkoutRecordsDto.WorkoutWithExercisesAndSets(workoutDto, exerciseWithSets)
}
