package com.lefarmico.exercise_menu.viewModel

import androidx.lifecycle.MutableLiveData
import com.lefarmico.core.base.BaseViewModel
import com.lefarmico.core.entity.LibraryViewData
import com.lefarmico.core.mapper.toViewDataCategory
import com.lefarmico.core.utils.SingleLiveEvent
import com.lefarmico.domain.entity.LibraryDto
import com.lefarmico.domain.repository.LibraryRepository
import com.lefarmico.domain.utils.DataState
import com.lefarmico.exercise_menu.intent.CategoryListIntent
import com.lefarmico.navigation.Router
import com.lefarmico.navigation.notification.Notification
import com.lefarmico.navigation.params.LibraryParams
import com.lefarmico.navigation.params.ToastBarParams
import com.lefarmico.navigation.screen.Screen
import io.reactivex.rxjava3.subjects.BehaviorSubject
import javax.inject.Inject

class CategoryListViewModel @Inject constructor() : BaseViewModel<CategoryListIntent>() {

    val categoriesLiveData = MutableLiveData<DataState<List<LibraryViewData.Category>>>()
    val notificationLiveData = SingleLiveEvent<String>()

    @Inject
    lateinit var repo: LibraryRepository
    @Inject
    lateinit var router: Router

    private fun getCategories() {
        repo.getCategories()
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
            title = categoryTitle
        )
        if (isFieldValid(title)) {
            repo.addCategory(category)
                .doAfterSuccess { getCategories() }
                .subscribe()
        }
    }

    private fun goToSubcategoryScreen(categoryId: Int, isFromWorkoutScreen: Boolean) {
        router.navigate(
            screen = Screen.SUBCATEGORY_LIST_SCREEN,
            data = LibraryParams.SubcategoryList(categoryId, isFromWorkoutScreen)
        )
    }

    private fun isFieldValid(field: String): Boolean {
        return when {
            field.isEmpty() -> {
                notificationLiveData.postValue("The field must not be empty.")
                false
            }
            isFieldExist(field) -> {
                notificationLiveData.postValue("$field field already exist.")
                false
            }
            else -> true
        }
    }

    private fun isFieldExist(field: String): Boolean {
        val subject = BehaviorSubject.create<Boolean>()
        repo.getCategories()
            .subscribe { dataState ->
                when (dataState) {
                    is DataState.Success -> {
                        subject.onNext(dataState.data.any { it.title == field })
                        subject.onComplete()
                    }
                    else -> {}
                }
            }

        return subject.blockingSingle()
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
