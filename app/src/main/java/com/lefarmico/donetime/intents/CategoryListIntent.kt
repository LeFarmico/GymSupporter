package com.lefarmico.donetime.intents

import com.lefarmico.donetime.views.base.BaseIntent

sealed class CategoryListIntent : BaseIntent() {
    
    data class AddCategory(val categoryTitle: String) : CategoryListIntent()

    object GetCategories : CategoryListIntent()
}
