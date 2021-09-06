package com.lefarmico.presentation.intents

import com.lefarmico.presentation.views.base.BaseIntent

sealed class CategoryListIntent : BaseIntent() {
    
    data class AddCategory(val categoryTitle: String) : CategoryListIntent()

    object GetCategories : CategoryListIntent()
}
