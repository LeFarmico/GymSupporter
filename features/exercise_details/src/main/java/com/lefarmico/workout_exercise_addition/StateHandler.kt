package com.lefarmico.workout_exercise_addition

import com.lefarmico.core.base.BaseState
import com.lefarmico.core.mapper.toViewData
import com.lefarmico.domain.entity.LibraryDto
import com.lefarmico.domain.utils.DataState

fun DataState<LibraryDto.Exercise>.reduce(): BaseState {
    return when (this) {
        is DataState.Error -> ExerciseDetailsEvent.ExceptionResult(exception)
        DataState.Loading -> ExerciseDetailsState.Loading
        is DataState.Success -> ExerciseDetailsState.ExerciseResult(data.toViewData())
    }
}
