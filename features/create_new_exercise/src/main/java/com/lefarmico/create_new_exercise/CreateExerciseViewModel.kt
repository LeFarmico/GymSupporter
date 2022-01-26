package com.lefarmico.create_new_exercise

import com.lefarmico.core.base.BaseViewModel
import com.lefarmico.core.extensions.debounceImmediate
import com.lefarmico.core.extensions.observeUi
import com.lefarmico.core.validator.EmptyValidator
import com.lefarmico.core.validator.ExistedValidator
import com.lefarmico.core.validator.ValidateHandler
import com.lefarmico.core.validator.ValidationState
import com.lefarmico.create_new_exercise.CreateExerciseIntent.*
import com.lefarmico.domain.entity.LibraryDto
import com.lefarmico.domain.repository.LibraryRepository
import com.lefarmico.navigation.Router
import com.lefarmico.navigation.notification.Notification
import com.lefarmico.navigation.params.ToastBarParams
import io.reactivex.rxjava3.core.Observable
import io.reactivex.rxjava3.core.Single
import io.reactivex.rxjava3.subjects.PublishSubject
import java.lang.Exception
import java.util.concurrent.TimeUnit
import javax.inject.Inject

class CreateExerciseViewModel @Inject constructor(
    private val repo: LibraryRepository,
    private val router: Router
) : BaseViewModel<CreateExerciseIntent, CreateExerciseState, CreateExerciseEvent>() {

    private val validator = ValidateHandler()
    private val validateSubject = PublishSubject.create<String>()
    private var validateCache = listOf<String>()

    init {
        validator()
    }

    private fun getExistedExercises(subcategoryId: Int) {
        repo.getExercises(subcategoryId)
            .observeUi()
            .doOnError { mState.postValue(CreateExerciseState.ExceptionResult(it as Exception)) }
            .doOnSuccess { dataState -> putToCache(dataState.reduce()) }
            .subscribe()
    }

    private fun putToCache(exerciseTitles: List<String>) {
        validateCache = exerciseTitles
    }

    private fun validator() {
        Observable.create<String> { input -> validateSubject.subscribe { input.onNext(it) } }
            .debounceImmediate(1, TimeUnit.SECONDS)
            .distinctUntilChanged()
            .observeUi()
            .doOnError { mState.postValue(CreateExerciseState.ExceptionResult(it as Exception)) }
            .doOnNext { validateField -> validate(validateField, validateCache) }
            .subscribe()
    }

    private fun validate(field: String, fieldList: List<String>) {
        validator.resetValidators()
        validator.addValidator(EmptyValidator(field))
        validator.addValidator(ExistedValidator(field, fieldList))

        val event = when (validator.validate()) {
            EmptyValidator.EmptyState -> CreateExerciseEvent.ValidationEmpty
            ExistedValidator.ExistedState -> CreateExerciseEvent.ValidationAlreadyExist
            ValidationState.SuccessState -> CreateExerciseEvent.ValidationSuccess
        }
        mEvent.postValue(event)
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
            .doOnError { mState.value = CreateExerciseState.ExerciseActionResult.Failure }
            .doAfterSuccess { mState.value = CreateExerciseState.ExerciseActionResult.Success }
            .subscribe()
    }

    private fun showToast(text: String) {
        router.show(Notification.TOAST, ToastBarParams(text))
    }

    private fun closeScreen(text: String) {
        Single.create<String> {
            showToast(text)
            it.onSuccess(text)
        }
            .doAfterSuccess { back() }
            .subscribe()
    }

    private fun back() {
        router.back()
    }

    override fun triggerIntent(intent: CreateExerciseIntent) {
        when (intent) {
            is ShowToast -> showToast(intent.text)
            is ValidateExercise -> validateSubject.onNext(intent.title)
            is GetExercises -> getExistedExercises(intent.subcategoryId)
            is AddExercise -> {
                intent.apply { addExercise(title, description, imageRes, subcategoryId) }
            }
            is CloseScreenWithToast -> closeScreen(intent.text)
        }
    }
}
