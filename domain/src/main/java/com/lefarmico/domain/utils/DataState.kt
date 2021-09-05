package com.lefarmico.domain.utils

import java.lang.Exception

sealed class DataState<out T> {

    data class Success<out T>(val data: T) : DataState<T>()
    
    data class Error(val exception: Exception) : DataState<Nothing>()
    
    object Loading : DataState<Nothing>()
    
    object Empty : DataState<Nothing>()
}
