package com.lefarmico.create_new_exercise.viewModel

import androidx.lifecycle.MutableLiveData
import com.lefarmico.core.base.BaseViewModel
import com.lefarmico.core.extensions.observeUi
import com.lefarmico.core.utils.SingleLiveEvent
import com.lefarmico.core.utils.ValidationState
import com.lefarmico.create_new_exercise.intent.AddExerciseIntent
import com.lefarmico.domain.entity.LibraryDto
import com.lefarmico.domain.repository.LibraryRepository
import com.lefarmico.domain.utils.DataState
import com.lefarmico.navigation.Router
import com.lefarmico.navigation.notification.Notification
import com.lefarmico.navigation.params.ToastBarParams
import javax.inject.Inject

class CreateNewExerciseViewModel @Inject constructor() : BaseViewModel<AddExerciseIntent>() {

    @Inject
    lateinit var repo: LibraryRepository
    @Inject
    lateinit var router: Router

    val addExerciseLiveData = MutableLiveData<DataState<Long>>()
    val notificationLiveData = SingleLiveEvent<String>()

    private fun addNewExercise(title: String, description: String, imageRes: String, subcategoryId: Int) {
        val exerciseTitle = title.trim()
        val exercise = LibraryDto.Exercise(
            title = title,
            description = description,
            imageRes = imageRes,
            subCategoryId = subcategoryId
        )
        repo.getExercises(subcategoryId)
            .observeUi()
            .doOnSuccess { dataState ->
                when (dataState) {
                    is DataState.Success -> {
                        when (isValidated(title, dataState.data)) {
                            is ValidationState.AlreadyExist -> validateAlreadyExistResolver(exerciseTitle)
                            is ValidationState.Success -> validateSuccessResolver(exercise)
                            ValidationState.Empty -> validateEmptyResolver()
                        }
                    }
                    DataState.Empty -> {
                        when (isValidated(title)) {
                            is ValidationState.AlreadyExist -> validateAlreadyExistResolver(exerciseTitle)
                            is ValidationState.Success -> validateSuccessResolver(exercise)
                            ValidationState.Empty -> validateEmptyResolver()
                        }
                    }
                    else -> { throw (IllegalArgumentException()) }
                }
            }.subscribe()
    }

    private fun validateSuccessResolver(exercise: LibraryDto.Exercise) {
        repo.addExercise(exercise)
            .observeUi()
            .doAfterSuccess { router.back() }
            .subscribe()
    }

    private fun validateEmptyResolver() {
        notificationLiveData.postValue("field should not be empty")
    }

    private fun validateAlreadyExistResolver(field: String) {
        notificationLiveData.postValue("$field category already exist")
    }

    private fun isValidated(field: String, fieldList: List<LibraryDto.Exercise> = listOf()): ValidationState {
        return when {
            field.isEmpty() -> ValidationState.Empty
            fieldList.none { it.title == field } -> ValidationState.Success(field)
            else -> ValidationState.AlreadyExist(field)
        }
    }

    private fun showToast(text: String) {
        router.show(Notification.TOAST, ToastBarParams(text))
    }

    private fun back() {
        router.back()
    }

    override fun onTriggerEvent(eventType: AddExerciseIntent) {
        when (eventType) {
            is AddExerciseIntent.AddExerciseResult -> {
                addNewExercise(
                    eventType.title,
                    eventType.description,
                    eventType.imageRes,
                    eventType.subcategoryId
                )
            }
            AddExerciseIntent.Back -> back()
            is AddExerciseIntent.ShowToast -> showToast(eventType.text)
        }
    }
}
