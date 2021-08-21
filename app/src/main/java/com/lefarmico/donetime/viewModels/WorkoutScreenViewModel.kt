package com.lefarmico.donetime.viewModels

import com.lefarmico.donetime.App
import com.lefarmico.donetime.data.Interactor
import com.lefarmico.donetime.data.entities.workout.WorkoutData
import com.lefarmico.donetime.views.base.BaseViewModel
import javax.inject.Inject

class WorkoutScreenViewModel : BaseViewModel() {

    @Inject lateinit var interactor: Interactor

    init {
        App.appComponent.inject(this)
    }

    fun putWorkoutNoteToDB(workoutData: WorkoutData) {
        interactor.addWorkoutNoteToDB(workoutData)
    }

    fun putWorkoutData(workoutData: WorkoutData) {
        interactor.addWorkoutData(workoutData)
    }
}
