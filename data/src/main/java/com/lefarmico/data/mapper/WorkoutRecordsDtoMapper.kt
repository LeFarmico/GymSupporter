package com.lefarmico.data.mapper

import com.lefarmico.data.db.entity.WorkoutRecordsData
import com.lefarmico.domain.entity.WorkoutRecordsDto
import java.lang.IllegalArgumentException

fun WorkoutRecordsData.Exercise.toDto() = WorkoutRecordsDto.Exercise(
    id = id,
    exerciseName = exerciseName,
    workoutId = workoutId
)

fun WorkoutRecordsData.Set.toDto() = WorkoutRecordsDto.Set(
    id = id,
    exerciseId = exerciseId,
    setNumber = setNumber,
    weight = weight,
    reps = reps,
    measureType = measureType.toDto()
)

fun WorkoutRecordsData.Workout.toDto() = WorkoutRecordsDto.Workout(
    id = id,
    date = date,
    title = title,
    time = time
)

@JvmName("WorkoutRecordsDataSetToDto")
fun List<WorkoutRecordsData.Set>.toDto() = this.map { it.toDto() }

@JvmName("WorkoutRecordsDataExerciseWithSetsToDto")
fun List<WorkoutRecordsData.ExerciseWithSets>.toDto() = this.map { it.toDto() }

@JvmName("WorkoutRecordsDataWorkoutWithExercisesAndSets")
fun List<WorkoutRecordsData.WorkoutWithExercisesAndSets>.toDto() =
    this.map { it.toDto() }

fun WorkoutRecordsData.MeasureType.toDto(): WorkoutRecordsDto.MeasureType {
    return when (this.typeNumber) {
        1 -> WorkoutRecordsDto.MeasureType.KILO
        2 -> WorkoutRecordsDto.MeasureType.LB
        else -> throw (IllegalArgumentException())
    }
}

fun WorkoutRecordsData.WorkoutWithExercisesAndSets.toDto() = WorkoutRecordsDto.WorkoutWithExercisesAndSets(
    workout = workout.toDto(),
    exerciseWithSetsList = exerciseWithSetsList.toDto()
)

fun WorkoutRecordsData.ExerciseWithSets.toDto() = WorkoutRecordsDto.ExerciseWithSets(
    exercise = exercise.toDto(),
    setList = setList.toDto()
)
