package com.lefarmico.exercise_menu.intent

import com.lefarmico.core.base.BaseIntent

sealed class CategoryListIntent : BaseIntent() {

    data class AddCategory(val categoryTitle: String) : CategoryListIntent()

    object GetCategories : CategoryListIntent()
}
