package com.lefarmico.exercise_menu.view

import com.lefarmico.core.entity.LibraryViewData

interface CategoryListView {

    fun showCategories(items: List<LibraryViewData.Category>)

    fun hideCategories()
}
