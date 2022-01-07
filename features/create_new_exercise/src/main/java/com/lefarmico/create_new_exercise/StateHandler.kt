package com.lefarmico.create_new_exercise

import com.lefarmico.core.mapper.toViewData
import com.lefarmico.domain.entity.LibraryDto
import com.lefarmico.domain.utils.DataState

@JvmName("reduceExercise")
fun DataState<List<LibraryDto.Exercise>>.reduce(): List<String> {
    return when (this) {
        DataState.Empty -> listOf()
        is DataState.Error -> throw (exception)
        DataState.Loading -> throw (IllegalArgumentException())
        is DataState.Success -> this.data.toViewData().map { it.title }
    }
}
