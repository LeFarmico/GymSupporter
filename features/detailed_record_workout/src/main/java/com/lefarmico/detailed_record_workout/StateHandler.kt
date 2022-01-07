package com.lefarmico.detailed_record_workout

import com.lefarmico.core.mapper.toViewData
import com.lefarmico.domain.entity.FormatterDto
import com.lefarmico.domain.entity.WorkoutRecordsDto
import com.lefarmico.domain.utils.DataState
import java.time.LocalDate
import java.time.format.DateTimeFormatter

fun DataState<WorkoutRecordsDto.WorkoutWithExercisesAndSets>.reduce(
    formatter: DateTimeFormatter
): DetailedState {
    return when (this) {
        DataState.Empty -> DetailedState.ExceptionResult(NullPointerException("Exercise is not Exist"))
        is DataState.Error -> DetailedState.ExceptionResult(exception)
        DataState.Loading -> DetailedState.Loading
        is DataState.Success -> DetailedState.WorkoutResult(data.toViewData(formatter))
    }
}

fun FormatterDto.reduce(localDate: LocalDate): DetailedState {
    val formattedDate = localDate.format(this.formatter)
    return DetailedState.DateResult(formattedDate)
}
