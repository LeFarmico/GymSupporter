package com.lefarmico.exercise_menu.viewModel

import androidx.lifecycle.MutableLiveData
import com.lefarmico.core.base.BaseViewModel
import com.lefarmico.domain.entity.LibraryDto
import com.lefarmico.domain.repository.LibraryRepository
import com.lefarmico.domain.utils.DataState
import com.lefarmico.exercise_menu.intent.SubCategoryIntent
import com.lefarmico.navigation.Router
import com.lefarmico.navigation.params.LibraryParams
import com.lefarmico.navigation.screen.Screen
import javax.inject.Inject

class SubCategoryViewModel @Inject constructor() : BaseViewModel<SubCategoryIntent>() {

    val subCategoriesLiveData = MutableLiveData<DataState<List<LibraryDto.SubCategory>>>()

    @Inject
    lateinit var repo: LibraryRepository

    @Inject
    lateinit var router: Router

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

    fun goToExerciseListScreen(categoryId: Int, subCategoryId: Int, isFromWorkoutScreen: Boolean) {
        router.navigate(
            screen = Screen.EXERCISE_LIST_SCREEN,
            data = LibraryParams.ExerciseList(categoryId, subCategoryId, isFromWorkoutScreen)
        )
    }

    override fun onTriggerEvent(eventType: SubCategoryIntent) {
        when (eventType) {
            is SubCategoryIntent.GetSubcategories -> getSubCategories(eventType.categoryId)
            is SubCategoryIntent.AddNewSubCategory -> addNewSubCategory(eventType.title, eventType.categoryId)
            is SubCategoryIntent.GoToExerciseListScreen -> goToExerciseListScreen(
                eventType.categoryId,
                eventType.subcategoryId,
                eventType.isFromWorkoutScreen
            )
        }
    }
}
