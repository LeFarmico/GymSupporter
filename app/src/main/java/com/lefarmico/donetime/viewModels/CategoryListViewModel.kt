package com.lefarmico.donetime.viewModels

import androidx.lifecycle.MutableLiveData
import com.lefarmico.donetime.App
import com.lefarmico.donetime.data.Interactor
import com.lefarmico.donetime.data.entities.library.LibraryCategory
import com.lefarmico.donetime.views.base.BaseViewModel
import javax.inject.Inject

class CategoryListViewModel : BaseViewModel() {

    val categoriesLiveData = MutableLiveData<List<LibraryCategory>>()

    init {
        App.appComponent.inject(this)
        passCategoriesToLiveData()
    }

    @Inject
    lateinit var interactor: Interactor

    private fun passCategoriesToLiveData() {
        interactor.getCategoriesFromDB()
            .subscribe {
                categoriesLiveData.postValue(it)
            }
    }

    fun addNewCategory(categoryTitle: String) {
        interactor.addNewCategory(categoryTitle)
    }
}
