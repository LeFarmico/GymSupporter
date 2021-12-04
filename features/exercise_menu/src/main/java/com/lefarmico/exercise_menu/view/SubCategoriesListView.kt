package com.lefarmico.exercise_menu.view

import com.lefarmico.core.entity.LibraryViewData

interface SubCategoriesListView {

    fun showSubcategories(items: List<LibraryViewData.SubCategory>)

    fun hideSubcategories()
}
