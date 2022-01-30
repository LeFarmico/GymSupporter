package com.lefarmico.exercise_menu.state

import com.lefarmico.core.base.BaseState
import com.lefarmico.core.entity.LibraryViewData
import java.lang.Exception

sealed class LibraryListState : BaseState.State {

    data class LibraryResult(val libraryList: List<LibraryViewData>) : LibraryListState()
    object Loading : LibraryListState()
    data class Title(val title: String) : LibraryListState()
}
