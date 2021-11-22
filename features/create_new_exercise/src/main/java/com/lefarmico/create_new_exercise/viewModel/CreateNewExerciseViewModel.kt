package com.lefarmico.create_new_exercise.viewModel

import androidx.lifecycle.MutableLiveData
import com.lefarmico.core.base.BaseViewModel
import com.lefarmico.core.extensions.observeUi
import com.lefarmico.core.utils.SingleLiveEvent
import com.lefarmico.core.utils.ValidationState
import com.lefarmico.create_new_exercise.intent.CreateExerciseIntent
import com.lefarmico.create_new_exercise.intent.CreateExerciseIntent.*
import com.lefarmico.domain.entity.LibraryDto
import com.lefarmico.domain.repository.LibraryRepository
import com.lefarmico.domain.utils.DataState
import com.lefarmico.navigation.Router
import com.lefarmico.navigation.notification.Notification
import com.lefarmico.navigation.params.ToastBarParams
import javax.inject.Inject

class CreateNewExerciseViewModel @Inject constructor() : BaseViewModel<CreateExerciseIntent>() {

    @Inject
    lateinit var repo: LibraryRepository
    @Inject
    lateinit var router: Router

    val addExerciseLiveData = MutableLiveData<DataState<Long>>()
    val notificationLiveData = SingleLiveEvent<String>()
    val validationLiveData = SingleLiveEvent<ValidationState>()

    private fun validateExercise(title: String, subcategoryId: Int) {
        val exerciseTitle = title.trim()
        repo.getExercises(subcategoryId)
            .observeUi()
            .doOnSuccess { dataState ->
                when (dataState) {
                    is DataState.Success -> validationLiveData.setValue(
                        validate(
                            exerciseTitle,
                            dataState.data
                        )
                    )
                    DataState.Empty -> validationLiveData.setValue(validate(title))
                    else -> throw (IllegalArgumentException())
                }
            }.subscribe()
    }

    private fun addExercise(title: String, description: String, imageRes: String, subcategoryId: Int) {
        val exercise = LibraryDto.Exercise(
            title = title,
            description = description,
            imageRes = imageRes,
            subCategoryId = subcategoryId
        )
        repo.addExercise(exercise)
            .observeUi()
            .doAfterSuccess { router.back() }
            .subscribe()
    }

    private fun validate(
        field: String,
        fieldList: List<LibraryDto.Exercise> = listOf()
    ): ValidationState {
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

    override fun onTriggerEvent(eventType: CreateExerciseIntent) {
        when (eventType) {
            Back -> back()
            is ShowToast -> showToast(eventType.text)
            is ValidateExercise -> validateExercise(eventType.title, eventType.subcategoryId)
            is AddExercise -> {
                eventType.apply { addExercise(title, description, imageRes, subcategoryId) }
            }
        }
    }
}
