package com.lefarmico.exercise_menu.viewModel

import androidx.lifecycle.MutableLiveData
import com.lefarmico.core.base.BaseViewModel
import com.lefarmico.core.entity.LibraryViewData
import com.lefarmico.core.extensions.observeUi
import com.lefarmico.core.mapper.toViewDataSubCategory
import com.lefarmico.core.utils.SingleLiveEvent
import com.lefarmico.core.utils.ValidationState
import com.lefarmico.domain.entity.LibraryDto
import com.lefarmico.domain.repository.LibraryRepository
import com.lefarmico.domain.utils.DataState
import com.lefarmico.exercise_menu.intent.SubCategoryIntent
import com.lefarmico.navigation.Router
import com.lefarmico.navigation.notification.Notification
import com.lefarmico.navigation.params.LibraryParams
import com.lefarmico.navigation.params.ToastBarParams
import com.lefarmico.navigation.screen.Screen
import javax.inject.Inject

class SubCategoryViewModel @Inject constructor() : BaseViewModel<SubCategoryIntent>() {

    val subCategoriesLiveData = MutableLiveData<DataState<List<LibraryViewData.SubCategory>>>()
    val notificationLiveData = SingleLiveEvent<String>()

    @Inject
    lateinit var repo: LibraryRepository

    @Inject
    lateinit var router: Router

    private fun getSubCategories(categoryId: Int) {
        repo.getSubCategories(categoryId)
            .observeUi()
            .subscribe { dataState ->
                when (dataState) {
                    DataState.Empty -> subCategoriesLiveData.postValue(DataState.Empty)
                    DataState.Loading -> subCategoriesLiveData.postValue(DataState.Loading)
                    is DataState.Error -> subCategoriesLiveData.postValue(dataState)
                    is DataState.Success -> {
                        val success = DataState.Success(dataState.data.toViewDataSubCategory())
                        subCategoriesLiveData.postValue(success)
                    }
                }
            }
    }

    private fun addNewSubCategory(subcategoryTitle: String, categoryId: Int) {
        val title = subcategoryTitle.trim()
        val subCategory = LibraryDto.SubCategory(
            title = title,
            categoryId = categoryId
        )
        repo.getSubCategories(categoryId)
            .observeUi()
            .doOnSuccess { dataState ->
                when (dataState) {
                    is DataState.Success -> {
                        when (isValidated(title, dataState.data)) {
                            is ValidationState.AlreadyExist -> validateAlreadyExistResolver(title)
                            is ValidationState.Success -> validateSuccessResolver(subCategory)
                            ValidationState.Empty -> validateEmptyResolver()
                        }
                    }
                    DataState.Empty -> {
                        when (isValidated(title)) {
                            is ValidationState.AlreadyExist -> validateAlreadyExistResolver(title)
                            is ValidationState.Success -> validateSuccessResolver(subCategory)
                            ValidationState.Empty -> validateEmptyResolver()
                        }
                    }
                    else -> { throw (IllegalArgumentException()) }
                }
            }.subscribe()
    }

    private fun validateSuccessResolver(subCategory: LibraryDto.SubCategory) {
        repo.addSubCategory(subCategory)
            .observeUi()
            .doAfterSuccess { getSubCategories(subCategory.categoryId) }
            .subscribe()
    }

    private fun validateEmptyResolver() {
        notificationLiveData.postValue("field should not be empty")
    }

    private fun validateAlreadyExistResolver(field: String) {
        notificationLiveData.postValue("$field category already exist")
    }

    private fun isValidated(field: String, fieldList: List<LibraryDto.SubCategory> = listOf()): ValidationState {
        return when {
            field.isEmpty() -> ValidationState.Empty
            fieldList.none { it.title == field } -> ValidationState.Success(field)
            else -> ValidationState.AlreadyExist(field)
        }
    }

    private fun goToExerciseListScreen(categoryId: Int, subCategoryId: Int, isFromWorkoutScreen: Boolean) {
        router.navigate(
            screen = Screen.EXERCISE_LIST_SCREEN,
            data = LibraryParams.ExerciseList(categoryId, subCategoryId, isFromWorkoutScreen)
        )
    }

    private fun showToast(text: String) {
        router.show(Notification.TOAST, ToastBarParams(text))
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
            is SubCategoryIntent.ShowToast -> showToast(eventType.text)
        }
    }
}
