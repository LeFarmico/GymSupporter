package com.lefarmico.donetime.intents

import com.lefarmico.donetime.views.base.BaseIntent

sealed class SubCategoryIntent : BaseIntent() {

    data class GetSubcategories(val categoryId: Int) : SubCategoryIntent()

    data class AddNewSubCategory(val title: String, val categoryId: Int?) : SubCategoryIntent()
}
