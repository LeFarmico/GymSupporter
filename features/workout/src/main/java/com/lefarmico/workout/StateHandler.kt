package com.lefarmico.workout

import com.lefarmico.core.mapper.toViewData
import com.lefarmico.domain.entity.CurrentWorkoutDto
import com.lefarmico.domain.utils.DataState
import java.time.LocalDate
import java.time.format.DateTimeFormatter

fun DataState<Long>.reduce(): WorkoutEvent {
    return when (this) {
        DataState.Empty -> WorkoutEvent.SetParamsDialog(-1)
        is DataState.Error -> WorkoutEvent.ExceptionEvent(exception)
        DataState.Loading -> WorkoutEvent.Loading
        is DataState.Success -> WorkoutEvent.SetParamsDialog(data)
    }
}

@JvmName("reduceExerciseWithSets")
fun DataState<List<CurrentWorkoutDto.ExerciseWithSets>>.reduce(): WorkoutState {
    return when (this) {
        DataState.Empty -> WorkoutState.ExerciseResult(listOf())
        is DataState.Error -> WorkoutState.ExceptionResult(exception)
        DataState.Loading -> WorkoutState.Loading
        is DataState.Success -> WorkoutState.ExerciseResult(data.toViewData())
    }
}

@JvmName("reduceLocalDate")
fun DataState<LocalDate>.reduce(formatter: DateTimeFormatter): WorkoutState {
    return when (this) {
        DataState.Empty -> WorkoutState.ExceptionResult(NullPointerException())
        is DataState.Error -> WorkoutState.ExceptionResult(exception)
        DataState.Loading -> WorkoutState.Loading
        is DataState.Success -> WorkoutState.DateResult(data.format(formatter))
    }
}
