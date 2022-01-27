package com.lefarmico.detailed_record_workout

import com.lefarmico.core.base.BaseViewModel
import com.lefarmico.core.extensions.observeUi
import com.lefarmico.domain.repository.WorkoutRecordsRepository
import com.lefarmico.domain.repository.manager.FormatterManager
import com.lefarmico.domain.repository.manager.FormatterTimeManager
import com.lefarmico.navigation.Router
import com.lefarmico.navigation.notification.Notification
import com.lefarmico.navigation.params.ToastBarParams
import com.lefarmico.navigation.params.WorkoutScreenParams
import com.lefarmico.navigation.screen.Screen
import io.reactivex.rxjava3.core.Single
import java.time.format.DateTimeFormatter
import javax.inject.Inject

class DetailedWorkoutRecordViewModel @Inject constructor(
    private val repo: WorkoutRecordsRepository,
    private val router: Router,
    private val formatterManager: FormatterManager,
    private val formatterTimeManager: FormatterTimeManager
) : BaseViewModel<DetailedIntent, DetailedState, DetailedEvent>() {

    private fun getRecordWorkout(workoutId: Int) {
        repo.getWorkoutWithExerciseAnsSets(workoutId)
            .observeUi()
            .doOnError { mEvent.postValue(DetailedEvent.DataLoadFailure) }
            .doAfterSuccess { dataState ->
                getSelectedFormatters { dateF, timeF -> mState.value = dataState.reduce(dateF, timeF) }
            }.subscribe()
    }

    private fun showToast(text: String) {
        router.show(Notification.TOAST, ToastBarParams(text))
    }

    private fun getSelectedFormatters(formatter: (DateTimeFormatter, DateTimeFormatter) -> Unit) {
        formatterManager.getSelectedFormatter()
            .observeUi()
            .zipWith(formatterTimeManager.getSelectedTimeFormatter()) { dateF, timeF ->
                Pair(dateF, timeF)
            }
            .doOnError { mEvent.postValue(DetailedEvent.DataLoadFailure) }
            .doAfterSuccess { pair ->
                formatter(pair.first.formatter, pair.second.formatter)
            }
            .subscribe()
    }

    private fun navigateToWorkout(workoutId: Int) {
        router.navigate(
            Screen.WORKOUT_SCREEN_FROM_DETAILED_SCREEN,
            WorkoutScreenParams.UpdateWorkout(workoutId)
        )
    }

    private fun closeWithToast(text: String) {
        Single.create<String> { emitter ->
            showToast(text)
            emitter.onSuccess(text)
        }
            .observeUi()
            .doAfterSuccess {
                router.back()
            }.subscribe()
    }

    override fun triggerIntent(intent: DetailedIntent) {
        return when (intent) {
            is DetailedIntent.GetWorkout -> getRecordWorkout(intent.workoutId)
            is DetailedIntent.ShowToast -> showToast(intent.text)
            is DetailedIntent.EditWorkout -> navigateToWorkout(intent.workoutId)
            is DetailedIntent.CloseWithToast -> closeWithToast(intent.errorText)
        }
    }
}
