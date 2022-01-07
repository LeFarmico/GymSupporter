package com.lefarmico.exercise_menu.intent

import com.lefarmico.core.base.BaseIntent
import com.lefarmico.core.entity.LibraryViewData

sealed class CategoryIntent : BaseIntent {

    data class Validate(val text: String) : CategoryIntent()

    data class AddCategory(val title: String) : CategoryIntent()

    data class ShowToast(val text: String) : CategoryIntent()

    object GetCategories : CategoryIntent()

    data class ClickItem(
        val item: LibraryViewData.Category,
        val isFromWorkoutScreen: Boolean
    ) : CategoryIntent()
}
