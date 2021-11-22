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
import com.lefarmico.domain.utils.map
import com.lefarmico.exercise_menu.intent.SubCategoryIntent
import com.lefarmico.exercise_menu.intent.SubCategoryIntent.*
import com.lefarmico.navigation.Router
import com.lefarmico.navigation.notification.Notification
import com.lefarmico.navigation.params.LibraryParams
import com.lefarmico.navigation.params.ToastBarParams
import com.lefarmico.navigation.screen.Screen
import javax.inject.Inject

class SubCategoryViewModel @Inject constructor() : BaseViewModel<SubCategoryIntent>() {

    val subCategoriesLiveData = MutableLiveData<DataState<List<LibraryViewData.SubCategory>>>()
    val notificationLiveData = SingleLiveEvent<String>()
    val validationLiveData = SingleLiveEvent<ValidationState>()

    @Inject
    lateinit var repo: LibraryRepository

    @Inject
    lateinit var router: Router

    private fun getSubCategories(categoryId: Int) {
        repo.getSubCategories(categoryId)
            .observeUi()
            .doOnSuccess { dataState ->
                val viewDataState = dataState.map { it.toViewDataSubCategory() }
                subCategoriesLiveData.postValue(viewDataState)
            }.subscribe()
    }

    private fun validateSubcategory(subcategoryTitle: String, categoryId: Int) {
        val title = subcategoryTitle.trim()
        repo.getSubCategories(categoryId)
            .observeUi()
            .doOnSuccess { dataState ->
                when (dataState) {
                    is DataState.Success -> validationLiveData.setValue(validate(title, dataState.data))
                    DataState.Empty -> validationLiveData.setValue(validate(title))
                    else -> throw (IllegalArgumentException())
                }
            }.subscribe()
    }

    private fun addSubcategory(subcategoryTitle: String, categoryId: Int) {
        val subCategory = LibraryDto.SubCategory(
            title = subcategoryTitle,
            categoryId = categoryId
        )
        repo.addSubCategory(subCategory)
            .observeUi()
            .doAfterSuccess { getSubCategories(subCategory.categoryId) }
            .subscribe()
    }

    private fun validate(
        field: String,
        fieldList: List<LibraryDto.SubCategory> = listOf()
    ): ValidationState {
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
            is GetSubcategories -> getSubCategories(eventType.categoryId)
            is ValidateSubcategory -> validateSubcategory(eventType.title, eventType.categoryId)
            is AddSubcategory -> addSubcategory(eventType.title, eventType.categoryId)
            is ShowToast -> showToast(eventType.text)
            is GoToExerciseListScreen -> goToExerciseListScreen(
                eventType.categoryId,
                eventType.subcategoryId,
                eventType.isFromWorkoutScreen
            )
        }
    }
}
