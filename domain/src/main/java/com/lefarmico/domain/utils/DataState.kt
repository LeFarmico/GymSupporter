package com.lefarmico.domain.utils

import java.lang.Exception

sealed class DataState<out T> {

    data class Success<out T>(val data: T) : DataState<T>()

    data class Error(val exception: Exception) : DataState<Nothing>()

    object Loading : DataState<Nothing>()
}

fun <T, R> DataState<T>.map(mapper: (T) -> R): DataState<R> {
    return when (this) {
        is DataState.Error -> DataState.Error(exception)
        DataState.Loading -> DataState.Loading
        is DataState.Success -> DataState.Success(mapper(this.data))
    }
}
