package com.lefarmico.exercise_menu

import com.lefarmico.domain.entity.LibraryDto
import com.lefarmico.domain.utils.DataState
import com.lefarmico.exercise_menu.state.LibraryListState

@JvmName("reduceCategoryTitle")
private fun DataState<LibraryDto.Category>.reduce(): LibraryListState {
    return when (this) {
        is DataState.Error -> LibraryListState.ExceptionResult(this.exception)
        DataState.Loading -> LibraryListState.Loading
        is DataState.Success -> LibraryListState.Title(this.data.title)
    }
}
