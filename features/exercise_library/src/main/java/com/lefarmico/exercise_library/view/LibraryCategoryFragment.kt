package com.lefarmico.exercise_library.view

import com.lefarmico.exercise_menu.view.CategoryListFragment

class LibraryCategoryFragment : com.lefarmico.exercise_menu.view.CategoryListFragment() {

    override val branchSubcategoryFragment = LibrarySubCategoryFragment::class.java
}
