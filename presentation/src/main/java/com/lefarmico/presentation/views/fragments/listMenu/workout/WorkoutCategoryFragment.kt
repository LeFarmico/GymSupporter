package com.lefarmico.donetime.views.fragments.listMenu.workout

import com.lefarmico.donetime.views.fragments.listMenu.CategoryListFragment

class WorkoutCategoryFragment : CategoryListFragment() {
    
    override val branchSubcategoryFragment = WorkoutSubcategoryFragment::class.java
}
