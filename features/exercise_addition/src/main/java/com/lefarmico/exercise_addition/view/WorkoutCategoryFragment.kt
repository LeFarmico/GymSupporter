package com.lefarmico.exercise_addition.view

import com.lefarmico.exercise_menu.view.CategoryListFragment

class WorkoutCategoryFragment : CategoryListFragment() {

    override val branchSubcategoryFragment = WorkoutSubcategoryFragment::class.java
}
