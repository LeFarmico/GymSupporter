package com.lefarmico.domain.utils

import java.lang.Exception

sealed class DataState<out T> {

    data class Success<out T>(val data: T) : DataState<T>()

    data class Error(val exception: Exception) : DataState<Nothing>()

    object Loading : DataState<Nothing>()

    object Empty : DataState<Nothing>()
}

fun <T, R> DataState<T>.map(mapper: (T) -> R): DataState<R> {
    return when (this) {
        DataState.Empty -> DataState.Empty
        is DataState.Error -> DataState.Error(exception)
        DataState.Loading -> DataState.Loading
        is DataState.Success -> DataState.Success(mapper(this.data))
    }
}

fun <T> DataState<T>.doOn(
    onSuccess: (DataState.Success<T>) -> Unit,
    onError: (DataState.Error) -> Unit,
    onEmpty: (DataState.Empty) -> Unit,
    onLoading: (DataState.Loading) -> Unit
) {
    when (this) {
        DataState.Empty -> onEmpty(DataState.Empty)
        is DataState.Error -> onError(this)
        DataState.Loading -> onLoading(DataState.Loading)
        is DataState.Success -> onSuccess(this)
    }
}
