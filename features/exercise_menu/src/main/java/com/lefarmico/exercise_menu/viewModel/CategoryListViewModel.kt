package com.lefarmico.exercise_menu.viewModel

import android.text.BoringLayout
import androidx.lifecycle.MutableLiveData
import com.lefarmico.core.base.BaseViewModel
import com.lefarmico.domain.entity.LibraryDto
import com.lefarmico.domain.repository.LibraryRepository
import com.lefarmico.domain.utils.DataState
import com.lefarmico.exercise_menu.intent.CategoryListIntent
import com.lefarmico.navigation.Router
import com.lefarmico.navigation.params.LibraryParams
import com.lefarmico.navigation.screen.Screen
import javax.inject.Inject

class CategoryListViewModel @Inject constructor() : BaseViewModel<CategoryListIntent>() {

    val categoriesLiveData = MutableLiveData<DataState<List<LibraryDto.Category>>>()

    @Inject
    lateinit var repo: LibraryRepository
    @Inject
    lateinit var router: Router

    private fun getCategories() {
        repo.getCategories()
            .subscribe { dataState ->
                categoriesLiveData.postValue(dataState)
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
            screen = Screen.SUBCATEGORY_SCREEN_FROM_LIBRARY,
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
