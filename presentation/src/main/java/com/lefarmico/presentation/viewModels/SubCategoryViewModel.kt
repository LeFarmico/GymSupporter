package com.lefarmico.presentation.viewModels

import androidx.lifecycle.MutableLiveData
import com.lefarmico.domain.entity.LibraryDto
import com.lefarmico.domain.repository.LibraryRepository
import com.lefarmico.domain.utils.DataState
import com.lefarmico.presentation.intents.SubCategoryIntent
import com.lefarmico.presentation.views.base.BaseViewModel
import javax.inject.Inject

class SubCategoryViewModel @Inject constructor() : BaseViewModel<SubCategoryIntent>() {

    val subCategoriesLiveData = MutableLiveData<DataState<List<LibraryDto.SubCategory>>>()
    
    @Inject
    lateinit var repo: LibraryRepository

    fun getSubCategories(categoryId: Int) {
        repo.getSubCategories(categoryId)
            .subscribe {
                subCategoriesLiveData.postValue(it)
            }
    }

    fun addNewSubCategory(title: String, categoryId: Int) {
        val category = LibraryDto.SubCategory(
            title = title,
            categoryId = categoryId
        )
        repo.addSubCategory(category)
            .subscribe { dataState ->
                when (dataState) {
                    is DataState.Success -> {
                        getSubCategories(categoryId)
                    }
                    else -> {}
                }
            }
    }

    override fun onTriggerEvent(eventType: SubCategoryIntent) {
        when (eventType) {
            is SubCategoryIntent.GetSubcategories -> getSubCategories(eventType.categoryId)
            is SubCategoryIntent.AddNewSubCategory -> addNewSubCategory(eventType.title, eventType.categoryId)
        }
    }
}
