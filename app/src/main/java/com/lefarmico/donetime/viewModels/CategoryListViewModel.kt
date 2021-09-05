package com.lefarmico.donetime.viewModels

import androidx.lifecycle.MutableLiveData
import com.lefarmico.data.repository.LibraryRepositoryImpl
import com.lefarmico.domain.entity.LibraryDto
import com.lefarmico.domain.utils.DataState
import com.lefarmico.donetime.App
import com.lefarmico.donetime.intents.CategoryListIntent
import com.lefarmico.donetime.views.base.BaseViewModel
import javax.inject.Inject

class CategoryListViewModel : BaseViewModel<CategoryListIntent>() {

    val categoriesLiveData = MutableLiveData<DataState<List<LibraryDto.Category>>>()

    init {
        App.appComponent.inject(this)
    }
    
    @Inject
    lateinit var repo: LibraryRepositoryImpl

    private fun getCategories() {
        repo.getCategories()
            .subscribe { dataState ->
                categoriesLiveData.postValue(dataState)
            }
    }

    fun addNewCategory(categoryTitle: String) {
        val category = LibraryDto.Category(
            title = categoryTitle
        )
        repo.addCategory(category)
            .subscribe { dataState ->
                when (dataState) {
                    DataState.Empty -> {
                        categoriesLiveData.postValue(DataState.Empty)
                    }
                    is DataState.Error -> {}
                    DataState.Loading -> {}
                    is DataState.Success -> {
                        getCategories()
                    }
                }
            }
    }

    override fun onTriggerEvent(eventType: CategoryListIntent) {
        when (eventType) {
            is CategoryListIntent.AddCategory -> addNewCategory(eventType.categoryTitle)
            CategoryListIntent.GetCategories -> getCategories()
        }
    }
}
