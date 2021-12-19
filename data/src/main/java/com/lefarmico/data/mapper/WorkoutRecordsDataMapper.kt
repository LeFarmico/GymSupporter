package com.lefarmico.data.mapper

import com.lefarmico.data.db.entity.WorkoutRecordsData
import com.lefarmico.domain.entity.WorkoutRecordsDto
import java.lang.IllegalArgumentException

fun WorkoutRecordsDto.Exercise.toData() = WorkoutRecordsData.Exercise(
    id = id,
    exerciseName = exerciseName,
    workoutId = workoutId
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
    title = title
)

fun List<WorkoutRecordsDto.Set>.toSetListData() = this.map { it.toData() }

fun List<WorkoutRecordsDto.Exercise>.toExerciseListData() = this.map { it.toData() }

fun List<WorkoutRecordsDto.Workout>.toWorkoutListData() = this.map { it.toData() }

fun List<WorkoutRecordsDto.ExerciseWithSets>.toExerciseWithSetsListData() = this.map { it.toData() }

fun List<WorkoutRecordsDto.WorkoutWithExercisesAndSets>.toWorkoutWithExercisesAndSetsData() =
    this.map { it.toData() }

fun WorkoutRecordsDto.MeasureType.toData(): WorkoutRecordsData.MeasureType {
    return when (this.typeNumber) {
        1 -> WorkoutRecordsData.MeasureType.KILO
        2 -> WorkoutRecordsData.MeasureType.LB
        else -> throw (IllegalArgumentException())
    }
}

fun WorkoutRecordsDto.WorkoutWithExercisesAndSets.toData() = WorkoutRecordsData.WorkoutWithExercisesAndSets(
    workout = workout.toData(),
    exerciseWithSetsList = exerciseWithSetsList.toExerciseWithSetsListData()
)

fun WorkoutRecordsDto.ExerciseWithSets.toData() = WorkoutRecordsData.ExerciseWithSets(
    exercise = exercise.toData(),
    setList = setList.toSetListData()
)
