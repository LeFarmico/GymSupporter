package com.lefarmico.donetime.viewModels

import com.lefarmico.donetime.App
import com.lefarmico.donetime.data.Interactor
import com.lefarmico.donetime.views.base.BaseViewModel
import javax.inject.Inject

class AddExerciseViewModel : BaseViewModel() {

    @Inject lateinit var interactor: Interactor

    init {
        App.appComponent.inject(this)
    }

    fun addNewExercise(title: String, description: String, imageRes: String, subcategoryId: Int) {
        interactor.addNewExercise(title, description, imageRes, subcategoryId)
    }
}
