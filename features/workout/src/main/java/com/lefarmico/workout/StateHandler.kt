package com.lefarmico.workout

import com.lefarmico.core.mapper.toViewData
import com.lefarmico.core.utils.Quad
import com.lefarmico.domain.entity.CurrentWorkoutDto
import com.lefarmico.domain.entity.WorkoutRecordsDto
import com.lefarmico.domain.utils.DataState
import java.time.LocalDate
import java.time.LocalTime
import java.time.format.DateTimeFormatter

fun DataState<Int>.reduce(): WorkoutEvent {
    return when (this) {
        is DataState.Error -> WorkoutEvent.ExceptionEvent(exception)
        DataState.Loading -> WorkoutEvent.Loading
        is DataState.Success -> WorkoutEvent.SetParamsDialog(data)
    }
}

@JvmName("reduceExerciseWithSets")
fun DataState<List<CurrentWorkoutDto.ExerciseWithSets>>.reduce(): WorkoutState {
    return when (this) {
        is DataState.Error -> WorkoutState.ExceptionResult(exception)
        DataState.Loading -> WorkoutState.Loading
        is DataState.Success -> WorkoutState.ExerciseResult(data.toViewData())
    }
}

@JvmName("reduceLocalDate")
fun DataState<LocalDate>.reduce(formatter: DateTimeFormatter): WorkoutState {
    return when (this) {
        is DataState.Error -> WorkoutState.ExceptionResult(exception)
        DataState.Loading -> WorkoutState.Loading
        is DataState.Success -> WorkoutState.DateResult(data.format(formatter))
    }
}

fun DataState<LocalTime>.reduce(formatter: DateTimeFormatter): WorkoutState {
    return when (this) {
        is DataState.Error -> throw (exception)
        DataState.Loading -> WorkoutState.Loading
        is DataState.Success -> WorkoutState.TimeResult(data.format(formatter))
    }
}

fun DataState<String>.reduceDate(): WorkoutState {
    return when (this) {
        is DataState.Error -> WorkoutState.ExceptionResult(exception)
        DataState.Loading -> WorkoutState.Loading
        is DataState.Success -> WorkoutState.DateResult(data)
    }
}
fun DataState<String>.reduceTime(): WorkoutState {
    return when (this) {
        is DataState.Error -> WorkoutState.ExceptionResult(exception)
        DataState.Loading -> WorkoutState.Loading
        is DataState.Success -> WorkoutState.TimeResult(data)
    }
}
fun DataState<Long>.reduceWorkoutId(): WorkoutState {
    return when (this) {
        is DataState.Error -> WorkoutState.ExceptionResult(exception)
        DataState.Loading -> WorkoutState.Loading
        is DataState.Success -> WorkoutState.EndWorkoutResult(data)
    }
}
fun DataState<WorkoutRecordsDto.WorkoutWithExercisesAndSets>.reduceWorkoutDto(): WorkoutRecordsDto.Workout {
    return when (this) {
        is DataState.Error -> throw (exception)
        DataState.Loading -> throw (NullPointerException())
        is DataState.Success -> data.workout
    }
}
fun Quad<String, DataState<LocalDate>, DataState<LocalTime>, DataState<List<CurrentWorkoutDto.ExerciseWithSets>>
    >.reduce(): Quad<LocalDate, LocalTime, String, List<CurrentWorkoutDto.ExerciseWithSets>> {
    val title = first
    val date = (second as DataState.Success).data
    val time = (third as DataState.Success).data
    val exercises = when (fourth) {
        is DataState.Success -> (fourth as DataState.Success<List<CurrentWorkoutDto.ExerciseWithSets>>).data
        else -> listOf()
    }
    return Quad(date, time, title, exercises)
}
