package com.lefarmico.create_new_exercise.viewModel

import androidx.lifecycle.MutableLiveData
import com.lefarmico.core.base.BaseViewModel
import com.lefarmico.core.utils.SingleLiveEvent
import com.lefarmico.create_new_exercise.intent.AddExerciseIntent
import com.lefarmico.domain.entity.LibraryDto
import com.lefarmico.domain.repository.LibraryRepository
import com.lefarmico.domain.utils.DataState
import com.lefarmico.navigation.Router
import com.lefarmico.navigation.notification.Notification
import com.lefarmico.navigation.params.ToastBarParams
import io.reactivex.rxjava3.subjects.BehaviorSubject
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
        if (isFieldValid(exerciseTitle, subcategoryId)) {
            repo.addExercise(exercise)
                .doAfterSuccess {
                    router.back()
                }
                .subscribe()
        }
    }

    private fun isFieldValid(field: String, subcategoryId: Int): Boolean {
        return when {
            field.isEmpty() -> {
                notificationLiveData.postValue("The field must not be empty.")
                false
            }
            isFieldExist(field, subcategoryId) -> {
                notificationLiveData.postValue("$field field already exist.")
                false
            }
            else -> true
        }
    }

    private fun isFieldExist(field: String, subcategoryId: Int): Boolean {
        val subject = BehaviorSubject.create<Boolean>()
        repo.getExercises(subcategoryId)
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
