package com.lefarmico.exercise_menu.action

import com.lefarmico.core.base.BaseAction

sealed class CategoryAction : BaseAction {
    data class Validate(val text: String) : CategoryAction()
    data class AddCategory(val title: String) : CategoryAction()
    data class ClickCategory(val categoryId: Int, val isFromWorkoutScreen: Boolean) : CategoryAction()
    data class ShowToast(val text: String) : CategoryAction()
    object GetCategories : CategoryAction()
}
