package com.lefarmico.donetime.viewModels

import androidx.lifecycle.MutableLiveData
import com.lefarmico.donetime.App
import com.lefarmico.donetime.data.Interactor
import com.lefarmico.donetime.data.entities.library.LibraryExercise
import com.lefarmico.donetime.views.base.BaseViewModel
import javax.inject.Inject

class ExerciseDetailsViewModel : BaseViewModel() {

    @Inject lateinit var interactor: Interactor

    val libraryExerciseLiveData = MutableLiveData<LibraryExercise>()

    init {
        App.appComponent.inject(this)
    }

    fun getExerciseFromDB(exerciseId: Int) {
        interactor.getExerciseById(exerciseId)
            .subscribe { data ->
                libraryExerciseLiveData.postValue(data)
            }
    }
}
