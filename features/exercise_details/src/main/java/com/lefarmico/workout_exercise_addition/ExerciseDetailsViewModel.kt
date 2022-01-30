package com.lefarmico.workout_exercise_addition

import com.lefarmico.core.base.BaseState
import com.lefarmico.core.base.BaseViewModel
import com.lefarmico.core.extensions.observeUi
import com.lefarmico.domain.repository.LibraryRepository
import com.lefarmico.navigation.Router
import com.lefarmico.navigation.notification.Notification
import com.lefarmico.navigation.params.ToastBarParams
import java.lang.Exception
import javax.inject.Inject

class ExerciseDetailsViewModel @Inject constructor(
    private val repo: LibraryRepository,
    private val router: Router
) : BaseViewModel<
    ExerciseDetailsIntent, ExerciseDetailsState, ExerciseDetailsEvent
    >() {

    override fun triggerIntent(intent: ExerciseDetailsIntent) {
        return when (intent) {
            is ExerciseDetailsIntent.GetExercise -> getExerciseFromDB(intent.exerciseId)
            is ExerciseDetailsIntent.ShowToast -> showToast(intent.text)
        }
    }

    private fun postEventState(state: BaseState) {
        when (state) {
            is BaseState.Event -> mEvent.postValue(state as ExerciseDetailsEvent)
            is BaseState.State -> mState.value = state as ExerciseDetailsState
        }
    }

    private fun getExerciseFromDB(exerciseId: Int) {
        repo.getExercise(exerciseId)
            .observeUi()
            .doOnError { postEventState(ExerciseDetailsEvent.ExceptionResult(it as Exception)) }
            .doAfterSuccess { dataState -> postEventState(dataState.reduce()) }
            .subscribe()
    }

    private fun showToast(text: String) {
        router.show(Notification.TOAST, ToastBarParams(text))
    }
}
