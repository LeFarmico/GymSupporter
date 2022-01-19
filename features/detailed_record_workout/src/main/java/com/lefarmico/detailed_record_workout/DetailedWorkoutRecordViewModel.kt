package com.lefarmico.detailed_record_workout

import com.lefarmico.core.base.BaseViewModel
import com.lefarmico.core.extensions.observeUi
import com.lefarmico.domain.repository.WorkoutRecordsRepository
import com.lefarmico.domain.repository.manager.FormatterManager
import com.lefarmico.domain.repository.manager.FormatterTimeManager
import com.lefarmico.navigation.Router
import com.lefarmico.navigation.notification.Notification
import com.lefarmico.navigation.params.ToastBarParams
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
            }.doAfterSuccess { pair ->
                formatter(pair.first.formatter, pair.second.formatter)
            }
            .subscribe()
    }

    override fun triggerIntent(intent: DetailedIntent) {
        return when (intent) {
            is DetailedIntent.GetWorkout -> getRecordWorkout(intent.workoutId)
            is DetailedIntent.ShowToast -> showToast(intent.text)
        }
    }
}
