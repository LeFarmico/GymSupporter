package com.lefarmico.donetime.viewModels

import androidx.lifecycle.MutableLiveData
import com.lefarmico.donetime.App
import com.lefarmico.donetime.data.Interactor
import com.lefarmico.donetime.data.entities.library.LibraryExercise
import com.lefarmico.donetime.views.base.BaseViewModel
import javax.inject.Inject

class ExerciseListViewModel : BaseViewModel() {

    @Inject lateinit var interactor: Interactor

    val exercisesLiveData = MutableLiveData<List<LibraryExercise>>()

    init {
        App.appComponent.inject(this)
    }

    fun passExercisesToLiveData(subCategoryId: Int) {
        interactor.getExercisesFromDB(subCategoryId)
            .subscribe {
                exercisesLiveData.postValue(it)
            }
    }
}
