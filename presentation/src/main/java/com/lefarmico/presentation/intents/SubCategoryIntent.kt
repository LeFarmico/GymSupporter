package com.lefarmico.presentation.intents

import com.lefarmico.presentation.views.base.BaseIntent

sealed class SubCategoryIntent : BaseIntent() {

    data class GetSubcategories(val categoryId: Int) : SubCategoryIntent()

    data class AddNewSubCategory(val title: String, val categoryId: Int) : SubCategoryIntent()
}
