package com.lefarmico.exercise_addition.view

import com.lefarmico.exercise_menu.view.SubCategoryListFragment

class WorkoutSubcategoryFragment : SubCategoryListFragment() {

    override val branchExerciseFragment = WorkoutExerciseFragment::class.java
}
