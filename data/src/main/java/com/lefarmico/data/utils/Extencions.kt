package com.lefarmico.data.utils

import com.lefarmico.domain.entity.WorkoutRecordsDto
import java.time.LocalDate
import java.util.*

fun List<WorkoutRecordsDto.ExerciseWithSets>.setWorkoutId(workoutId: Int) = this.map {
    WorkoutRecordsDto.ExerciseWithSets(
        exercise = WorkoutRecordsDto.Exercise(
            workoutId = workoutId,
            exerciseName = it.exercise.exerciseName
        ),
        setList = it.setList
    )
}

fun List<WorkoutRecordsDto.Set>.setExerciseId(exerciseId: Int) = this.map {
    WorkoutRecordsDto.Set(
        exerciseId = exerciseId,
        setNumber = it.setNumber,
        weight = it.weight,
        reps = it.reps,
        measureType = WorkoutRecordsDto.MeasureType.KILO
    )
}

fun LocalDate.getMonthDatesInRange(): List<LocalDate> {
    val dates = mutableListOf<LocalDate>()
    var localDate = this.withDayOfMonth(1)
    val monthLength = localDate.lengthOfMonth()
    while (dates.size <= monthLength - 1) {
        dates.add(localDate)
        localDate = localDate.plusDays(1)
    }
    return dates
}
