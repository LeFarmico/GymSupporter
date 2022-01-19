package com.lefarmico.exercise_menu.intent

import com.lefarmico.core.base.BaseIntent
import com.lefarmico.core.entity.LibraryViewData

sealed class SubcategoryIntent : BaseIntent {

    data class GetSubcategories(val categoryId: Int) : SubcategoryIntent()

    data class Validate(val title: String) : SubcategoryIntent()

    data class AddSubcategory(val title: String, val categoryId: Int) : SubcategoryIntent()

    data class ClickItem(
        val item: LibraryViewData.SubCategory,
        val isFromWorkoutScreen: Boolean
    ) : SubcategoryIntent()

    data class ShowToast(val text: String) : SubcategoryIntent()

    data class EditState(val action: Action) : SubcategoryIntent() {
        sealed class Action {
            object Show : Action()
            object Hide : Action()
            object SelectAll : Action()
            object DeselectAll : Action()
            object DeleteSelected : Action()
        }
    }

    data class DeleteSubCategory(val subcategoryId: Int, val categoryId: Int) : SubcategoryIntent()
}
