package com.lefarmico.exercise_menu.viewModel

import androidx.lifecycle.MutableLiveData
import com.lefarmico.core.base.BaseViewModel
import com.lefarmico.core.entity.LibraryViewData
import com.lefarmico.core.extensions.observeUi
import com.lefarmico.core.mapper.toViewDataCategory
import com.lefarmico.core.utils.SingleLiveEvent
import com.lefarmico.core.utils.ValidationState
import com.lefarmico.domain.entity.LibraryDto
import com.lefarmico.domain.repository.LibraryRepository
import com.lefarmico.domain.utils.DataState
import com.lefarmico.exercise_menu.intent.CategoryListIntent
import com.lefarmico.navigation.Router
import com.lefarmico.navigation.notification.Notification
import com.lefarmico.navigation.params.LibraryParams
import com.lefarmico.navigation.params.ToastBarParams
import com.lefarmico.navigation.screen.Screen
import javax.inject.Inject

class CategoryListViewModel @Inject constructor() : BaseViewModel<CategoryListIntent>() {

    val categoriesLiveData = MutableLiveData<DataState<List<LibraryViewData.Category>>>()
    val notificationLiveData = SingleLiveEvent<String>()
    val validationLiveData = SingleLiveEvent<ValidationState>()

    @Inject
    lateinit var repo: LibraryRepository
    @Inject
    lateinit var router: Router

    private fun getCategories() {
        repo.getCategories()
            .observeUi()
            .subscribe { dataState ->
                when (dataState) {
                    DataState.Empty -> categoriesLiveData.postValue(DataState.Empty)
                    DataState.Loading -> categoriesLiveData.postValue(DataState.Loading)
                    is DataState.Error -> categoriesLiveData.postValue(dataState)
                    is DataState.Success -> {
                        val success = DataState.Success(dataState.data.toViewDataCategory())
                        categoriesLiveData.postValue(success)
                    }
                }
            }
    }

    private fun addNewCategory(categoryTitle: String) {
        val title = categoryTitle.trim()
        val category = LibraryDto.Category(
            title = title
        )
        repo.getCategories()
            .observeUi()
            .doOnSuccess { dataState ->
                when (dataState) {
                    is DataState.Success -> {
                        when (isValidated(title, dataState.data)) {
                            is ValidationState.AlreadyExist -> validateAlreadyExistResolver(title)
                            is ValidationState.Success -> validateSuccessResolver(category)
                            ValidationState.Empty -> validateEmptyResolver()
                        }
                    }
                    DataState.Empty -> {
                        when (isValidated(title)) {
                            is ValidationState.AlreadyExist -> validateAlreadyExistResolver(title)
                            is ValidationState.Success -> validateSuccessResolver(category)
                            ValidationState.Empty -> validateEmptyResolver()
                        }
                    }
                    else -> { throw (IllegalArgumentException()) }
                }
            }.subscribe()
    }

    private fun validateSuccessResolver(category: LibraryDto.Category) {
        repo.addCategory(category)
            .observeUi()
            .doAfterSuccess { getCategories() }
            .subscribe()
    }

    private fun validateEmptyResolver() {
        notificationLiveData.postValue("field should not be empty")
    }

    private fun validateAlreadyExistResolver(field: String) {
        notificationLiveData.postValue("$field category already exist")
    }

    private fun isValidated(field: String, fieldList: List<LibraryDto.Category> = listOf()): ValidationState {
        return when {
            field.isEmpty() -> ValidationState.Empty
            fieldList.none { it.title == field } -> ValidationState.Success(field)
            else -> ValidationState.AlreadyExist(field)
        }
    }

    private fun goToSubcategoryScreen(categoryId: Int, isFromWorkoutScreen: Boolean) {
        router.navigate(
            screen = Screen.SUBCATEGORY_LIST_SCREEN,
            data = LibraryParams.SubcategoryList(categoryId, isFromWorkoutScreen)
        )
    }

    private fun showToast(text: String) {
        router.show(Notification.TOAST, ToastBarParams(text))
    }

    override fun onTriggerEvent(eventType: CategoryListIntent) {
        when (eventType) {
            is CategoryListIntent.AddCategory -> addNewCategory(eventType.categoryTitle)
            is CategoryListIntent.GetCategories -> getCategories()
            is CategoryListIntent.GoToSubcategoryScreen -> goToSubcategoryScreen(
                eventType.categoryId,
                eventType.isFromWorkoutScreen
            )
            is CategoryListIntent.ShowToast -> showToast(eventType.text)
        }
    }
}
