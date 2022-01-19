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

    data class EditState(val action: Action) : CategoryIntent() {
        sealed class Action {
            object Show : Action()
            object Hide : Action()
            object SelectAll : Action()
            object DeselectAll : Action()
            object DeleteSelected : Action()
        }
    }

    data class DeleteCategory(val categoryId: Int) : CategoryIntent()
}
