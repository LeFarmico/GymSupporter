package com.lefarmico.exercise_menu.viewModel

import androidx.lifecycle.MutableLiveData
import com.lefarmico.core.base.BaseViewModel
import com.lefarmico.core.entity.LibraryViewData
import com.lefarmico.core.mapper.toViewDataCategory
import com.lefarmico.domain.entity.LibraryDto
import com.lefarmico.domain.repository.LibraryRepository
import com.lefarmico.domain.utils.DataState
import com.lefarmico.exercise_menu.intent.CategoryListIntent
import com.lefarmico.navigation.Router
import com.lefarmico.navigation.params.LibraryParams
import com.lefarmico.navigation.screen.Screen
import javax.inject.Inject

class CategoryListViewModel @Inject constructor() : BaseViewModel<CategoryListIntent>() {

    val categoriesLiveData = MutableLiveData<DataState<List<LibraryViewData.Category>>>()

    @Inject
    lateinit var repo: LibraryRepository
    @Inject
    lateinit var router: Router

    private fun getCategories() {
        repo.getCategories()
            .subscribe { dataState ->
                when (dataState) {
                    is DataState.Success -> {
                        val success = DataState.Success(dataState.data.toViewDataCategory())
                        categoriesLiveData.postValue(success)
                    }
                    else -> {
                        categoriesLiveData.postValue(
                            dataState as DataState<List<LibraryViewData.Category>>
                        )
                    }
                }
            }
    }

    private fun addNewCategory(categoryTitle: String) {
        if (categoryTitle != "") {
            val category = LibraryDto.Category(
                title = categoryTitle
            )
            repo.addCategory(category)
                .subscribe { dataState ->
                    when (dataState) {
                        is DataState.Error -> {
                            categoriesLiveData.postValue(dataState)
                        }
                        DataState.Loading -> {
                            categoriesLiveData.postValue(DataState.Loading)
                        }
                        is DataState.Success -> {
                            getCategories()
                        }
                        else -> {}
                    }
                }
        } else {
            categoriesLiveData.postValue(DataState.Error(IllegalArgumentException()))
        }
    }

    private fun goToSubcategoryScreen(categoryId: Int, isFromWorkoutScreen: Boolean) {
        router.navigate(
            screen = Screen.SUBCATEGORY_LIST_SCREEN,
            data = LibraryParams.SubcategoryList(categoryId, isFromWorkoutScreen)
        )
    }

    override fun onTriggerEvent(eventType: CategoryListIntent) {
        when (eventType) {
            is CategoryListIntent.AddCategory -> addNewCategory(eventType.categoryTitle)
            is CategoryListIntent.GetCategories -> getCategories()
            is CategoryListIntent.GoToSubcategoryScreen -> goToSubcategoryScreen(
                eventType.categoryId,
                eventType.isFromWorkoutScreen
            )
        }
    }
}
