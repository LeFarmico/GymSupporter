package com.lefarmico.data.extensions

import com.lefarmico.domain.utils.DataState
import java.lang.Exception

fun <T> dataStateResolver(action: () -> T): DataState<T> {
    return try {
        val result = action()
        DataState.Success(result)
    } catch (e: Exception) {
        DataState.Error(e)
    }
}
