package com.lefarmico.data.extensions

import com.lefarmico.domain.utils.DataState
import java.lang.Exception

fun <T> dataStateResolver(list: List<T>): DataState<List<T>> {
    return if (list.isEmpty()) {
        DataState.Empty
    } else {
        DataState.Success(list)
    }
}

fun <T> dataStateActionResolver(action: () -> T): DataState<T> {
    return try {
        val result = action()
        DataState.Success(result)
    } catch (e: Exception) {
        DataState.Error(e)
    }
}

fun <T> dataStateNullResolver(item: T): DataState<T> {
    return if (item == null) {
        DataState.Empty
    } else {
        DataState.Success(item)
    }
}
