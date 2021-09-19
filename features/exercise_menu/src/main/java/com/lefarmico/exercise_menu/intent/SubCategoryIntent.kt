package com.lefarmico.exercise_menu.intent

import com.lefarmico.core.base.BaseIntent

sealed class SubCategoryIntent : BaseIntent() {

    data class GetSubcategories(val categoryId: Int) : SubCategoryIntent()

    data class AddNewSubCategory(val title: String, val categoryId: Int) : SubCategoryIntent()
}
