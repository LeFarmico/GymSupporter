package com.lefarmico.exercise_library.view

import com.lefarmico.exercise_menu.view.SubCategoryListFragment

class LibrarySubCategoryFragment : SubCategoryListFragment() {

    override val branchExerciseFragment = LibraryExerciseFragment::class.java
}
