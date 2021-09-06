package com.lefarmico.presentation.views.fragments.listMenu.workout

import com.lefarmico.presentation.views.fragments.listMenu.CategoryListFragment

class WorkoutCategoryFragment : CategoryListFragment() {
    
    override val branchSubcategoryFragment = WorkoutSubcategoryFragment::class.java
}
