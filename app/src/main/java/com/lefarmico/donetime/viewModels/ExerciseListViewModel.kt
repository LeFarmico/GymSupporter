package com.lefarmico.donetime.viewModels

import androidx.lifecycle.MutableLiveData
import com.lefarmico.donetime.App
import com.lefarmico.donetime.data.Interactor
import com.lefarmico.donetime.data.entities.library.ItemLibraryCategory
import com.lefarmico.donetime.data.entities.library.ItemLibraryExercise
import com.lefarmico.donetime.data.entities.library.ItemLibrarySubCategory
import com.lefarmico.donetime.views.base.BaseViewModel
import javax.inject.Inject

class ExerciseListViewModel : BaseViewModel() {

    @Inject lateinit var interactor: Interactor

    val categoriesLiveData = MutableLiveData<List<ItemLibraryCategory>>()
    val subCategoriesLiveData = MutableLiveData<List<ItemLibrarySubCategory>>()
    val exercisesLiveData = MutableLiveData<List<ItemLibraryExercise>>()

    init {
        App.appComponent.inject(this)
        passCategoriesToLiveData()
    }

    private fun passCategoriesToLiveData() {
        interactor.getCategoriesFromDB()
            .subscribe {
                categoriesLiveData.postValue(it)
            }
    }
    
    fun passSubCategoryToLiveData(categoryId: Int) {
        interactor.getSubCategoriesFromDB(categoryId)
            .subscribe {
                subCategoriesLiveData.postValue(it)
            }
    }

    fun passExercisesToLiveData(subCategoryId: Int) {
        interactor.getExercisesFromDB(subCategoryId)
            .subscribe {
                exercisesLiveData.postValue(it)
            }
    }
}
