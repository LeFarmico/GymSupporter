package com.lefarmico.home

import com.lefarmico.core.mapper.toViewData
import com.lefarmico.domain.entity.CalendarItemDto
import com.lefarmico.domain.entity.WorkoutRecordsDto
import com.lefarmico.domain.utils.DataState
import java.time.LocalDate
import java.time.format.DateTimeFormatter

@JvmName("reduceWorkoutWithExercisesAndSets")
fun DataState<List<WorkoutRecordsDto.WorkoutWithExercisesAndSets>>.reduce(
    formatter: DateTimeFormatter
): HomeState {
    return when (this) {
        is DataState.Error -> HomeState.ExceptionResult(this.exception)
        DataState.Loading -> HomeState.Loading
        is DataState.Success -> HomeState.WorkoutResult(this.data.toViewData(formatter))
    }
}
@JvmName("reduceCalendarItemDto")
fun DataState<List<CalendarItemDto>>.reduce(): HomeState {
    return when (this) {
        is DataState.Error -> HomeState.ExceptionResult(this.exception)
        DataState.Loading -> HomeState.Loading
        is DataState.Success -> HomeState.CalendarResult(this.data.toViewData())
    }
}

@JvmName("reduceMonthAndYearText")
fun DataState<String>.reduce(): HomeState {
    return when (this) {
        is DataState.Error -> HomeState.ExceptionResult(this.exception)
        DataState.Loading -> HomeState.Loading
        is DataState.Success -> HomeState.MonthAndYearResult(MonthAndYearText(this.data))
    }
}

fun DataState<LocalDate>.resolve(): LocalDate {
    return when (this) {
        is DataState.Error -> throw (exception)
        DataState.Loading -> throw (IllegalArgumentException())
        is DataState.Success -> data
    }
}

@JvmInline
value class MonthAndYearText(val text: String)
