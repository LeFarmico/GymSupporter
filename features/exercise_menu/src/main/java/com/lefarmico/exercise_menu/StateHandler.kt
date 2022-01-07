package com.lefarmico.exercise_menu

import com.lefarmico.core.entity.LibraryViewData
import com.lefarmico.domain.entity.LibraryDto
import com.lefarmico.domain.utils.DataState
import com.lefarmico.exercise_menu.state.LibraryListState

fun DataState<List<LibraryDto>>.reduce(): LibraryListState {
    return when (this) {
        DataState.Empty -> LibraryListState.LibraryResult(listOf())
        is DataState.Error -> LibraryListState.ExceptionResult(this.exception)
        DataState.Loading -> LibraryListState.Loading
        is DataState.Success -> LibraryListState.LibraryResult(data.map { it.toViewData() })
    }
}

private fun LibraryDto.toViewData(): LibraryViewData {
    return when (this) {
        is LibraryDto.Category -> LibraryViewData.Category(id, title)
        is LibraryDto.SubCategory -> LibraryViewData.SubCategory(id, title, categoryId)
        is LibraryDto.Exercise -> LibraryViewData.Exercise(id, title, description, imageRes, subCategoryId)
    }
}