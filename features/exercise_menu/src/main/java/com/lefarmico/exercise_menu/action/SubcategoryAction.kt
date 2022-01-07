package com.lefarmico.exercise_menu.action

import com.lefarmico.core.base.BaseAction
import com.lefarmico.core.entity.LibraryViewData

sealed class SubcategoryAction : BaseAction {
    data class Validate(val text: String) : SubcategoryAction()

    data class AddSubcategory(val title: String, val categoryId: Int) : SubcategoryAction()

    data class ShowToast(val text: String) : SubcategoryAction()

    data class GetSubcategories(val categoryId: Int) : SubcategoryAction()

    data class ClickSubcategory(
        val subcategory: LibraryViewData.SubCategory,
        val isFromWorkoutScreen: Boolean
    ) : SubcategoryAction()
}
