package com.lefarmico.core.mapper

import com.lefarmico.domain.entity.CurrentWorkoutDto
import com.lefarmico.domain.entity.WorkoutRecordsDto

fun WorkoutRecordsDto.ExerciseWithSets.toCurrent(): CurrentWorkoutDto.ExerciseWithSets {
    return CurrentWorkoutDto.ExerciseWithSets(
        exercise.toCurrent(),
        setList.toCurrent()
    )
}

@JvmName("WorkoutRecordsDtoExerciseWithSetsToCurrent")
fun List<WorkoutRecordsDto.ExerciseWithSets>.toCurrent() = this.map { it.toCurrent() }

fun WorkoutRecordsDto.Exercise.toCurrent() = CurrentWorkoutDto.Exercise(id, libraryId, exerciseName)

fun WorkoutRecordsDto.Set.toCurrent() = CurrentWorkoutDto.Set(id, exerciseId, setNumber, weight, reps)

@JvmName("WorkoutRecordsDtoSetToCurrent")
fun List<WorkoutRecordsDto.Set>.toCurrent() = this.map { it.toCurrent() }
