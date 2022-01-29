package com.lefarmico.detailed_record_workout

import com.lefarmico.core.base.BaseState
import com.lefarmico.core.mapper.toViewData
import com.lefarmico.domain.entity.WorkoutRecordsDto
import com.lefarmico.domain.utils.DataState
import java.time.format.DateTimeFormatter

fun DataState<WorkoutRecordsDto.WorkoutWithExercisesAndSets>.reduce(
    dateFormatter: DateTimeFormatter,
    timeFormatter: DateTimeFormatter
): BaseState {
    return when (this) {
        is DataState.Error -> DetailedState.ExceptionResult(exception)
        DataState.Loading -> DetailedEvent.Loading
        is DataState.Success -> DetailedState.WorkoutResult(data.toViewData(dateFormatter, timeFormatter))
    }
}
