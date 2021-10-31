package com.lefarmico.exercise_menu.viewModel

import androidx.lifecycle.MutableLiveData
import com.lefarmico.core.base.BaseViewModel
import com.lefarmico.core.entity.LibraryViewData
import com.lefarmico.core.mapper.toViewDataSubCategory
import com.lefarmico.core.utils.SingleLiveEvent
import com.lefarmico.domain.entity.LibraryDto
import com.lefarmico.domain.repository.LibraryRepository
import com.lefarmico.domain.utils.DataState
import com.lefarmico.exercise_menu.intent.SubCategoryIntent
import com.lefarmico.navigation.Router
import com.lefarmico.navigation.notification.Notification
import com.lefarmico.navigation.params.LibraryParams
import com.lefarmico.navigation.params.ToastBarParams
import com.lefarmico.navigation.screen.Screen
import io.reactivex.rxjava3.subjects.BehaviorSubject
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
        val category = LibraryDto.SubCategory(
            title = title,
            categoryId = categoryId
        )
        if (isFieldValid(subcategoryTitle, categoryId)) {
            repo.addSubCategory(category)
                .doAfterSuccess { getSubCategories(categoryId) }
                .subscribe()
        }
    }

    private fun isFieldValid(field: String, categoryId: Int): Boolean {
        return when {
            field.isEmpty() -> {
                notificationLiveData.postValue("The field must not be empty.")
                false
            }
            isFieldExist(field, categoryId) -> {
                notificationLiveData.postValue("$field field already exist.")
                false
            }
            else -> true
        }
    }

    private fun isFieldExist(field: String, categoryId: Int): Boolean {
        val subject = BehaviorSubject.create<Boolean>()
        repo.getSubCategories(categoryId)
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
