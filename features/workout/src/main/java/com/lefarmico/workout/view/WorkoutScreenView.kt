package com.lefarmico.workout.view

import com.lefarmico.core.entity.CurrentWorkoutViewData

interface WorkoutScreenView {

    fun showLoading()

    fun showSuccess()

    fun showError(e: Exception)

    fun showEmpty()

    fun showExercises(items: List<CurrentWorkoutViewData.ExerciseWithSets>)

    fun hideExercises()

    fun showEditState()

    fun hideEditState()
}
