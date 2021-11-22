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
import com.lefarmico.domain.utils.map
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
                val viewDataState = dataState.map { it.toViewDataCategory() }
                categoriesLiveData.postValue(viewDataState)
            }
    }

    private fun validateCategory(categoryTitle: String) {
        val title = categoryTitle.trim()
        repo.getCategories()
            .observeUi()
            .doOnSuccess { dataState ->
                when (dataState) {
                    is DataState.Success -> validationLiveData.setValue(validate(title, dataState.data))
                    DataState.Empty -> validationLiveData.setValue(validate(title))
                    else -> throw (IllegalArgumentException())
                }
            }.subscribe()
    }

    private fun addCategory(categoryTitle: String) {
        val category = LibraryDto.Category(title = categoryTitle)
        repo.addCategory(category)
            .observeUi()
            .doAfterSuccess { getCategories() }
            .subscribe()
    }

    private fun validate(
        field: String,
        fieldList: List<LibraryDto.Category> = listOf()
    ): ValidationState {
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
            is CategoryListIntent.ValidateCategory -> validateCategory(eventType.categoryTitle)
            is CategoryListIntent.GetCategories -> getCategories()
            is CategoryListIntent.ShowToast -> showToast(eventType.text)
            is CategoryListIntent.AddCategory -> addCategory(eventType.categoryTitle)
            is CategoryListIntent.GoToSubcategoryScreen -> goToSubcategoryScreen(
                eventType.categoryId,
                eventType.isFromWorkoutScreen
            )
        }
    }
}
