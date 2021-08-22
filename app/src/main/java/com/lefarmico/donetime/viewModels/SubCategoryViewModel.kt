package com.lefarmico.donetime.viewModels

import androidx.lifecycle.MutableLiveData
import com.lefarmico.donetime.App
import com.lefarmico.donetime.data.Interactor
import com.lefarmico.donetime.data.entities.library.LibrarySubCategory
import com.lefarmico.donetime.views.base.BaseViewModel
import javax.inject.Inject

class SubCategoryViewModel : BaseViewModel() {

    val subCategoriesLiveData = MutableLiveData<List<LibrarySubCategory>>()

    @Inject
    lateinit var interactor: Interactor

    init {
        App.appComponent.inject(this)
    }

    fun passSubCategoryToLiveData(categoryId: Int) {
        interactor.getSubCategoriesFromDB(categoryId)
            .subscribe {
                subCategoriesLiveData.postValue(it)
            }
    }
}
