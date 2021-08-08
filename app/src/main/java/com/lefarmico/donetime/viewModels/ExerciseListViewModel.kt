package com.lefarmico.donetime.viewModels

import androidx.lifecycle.MutableLiveData
import com.lefarmico.donetime.App
import com.lefarmico.donetime.data.Interactor
import com.lefarmico.donetime.data.entities.library.ItemLibraryCategory
import com.lefarmico.donetime.views.base.BaseViewModel
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers
import javax.inject.Inject

class ExerciseListViewModel : BaseViewModel() {

    @Inject lateinit var interactor: Interactor

    val categoriesLiveData = MutableLiveData<List<ItemLibraryCategory>>()

    init {
        App.appComponent.inject(this)
        passCategoriesToLiveData()
    }

    private fun passCategoriesToLiveData() {
        interactor.getCategoriesFromDB()
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe {
                categoriesLiveData.postValue(it)
            }
    }
}
