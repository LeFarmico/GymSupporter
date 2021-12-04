package com.lefarmico.exercise_menu.view

import com.lefarmico.core.entity.LibraryViewData

interface ExerciseListView {

    fun showExercises(items: List<LibraryViewData.Exercise>)

    fun hideExercises()
}
