package com.lefarmico.detailed_record_workout

import com.lefarmico.core.base.BaseViewModel
import com.lefarmico.core.extensions.observeUi
import com.lefarmico.domain.repository.WorkoutRecordsRepository
import com.lefarmico.domain.repository.manager.FormatterManager
import com.lefarmico.navigation.Router
import com.lefarmico.navigation.notification.Notification
import com.lefarmico.navigation.params.ToastBarParams
import java.time.format.DateTimeFormatter
import javax.inject.Inject

class DetailedWorkoutRecordViewModel @Inject constructor() : BaseViewModel<
    DetailedIntent, DetailedState, DetailedEvent>() {

    @Inject lateinit var repo: WorkoutRecordsRepository
    @Inject lateinit var router: Router
    @Inject lateinit var formatterManager: FormatterManager

    private fun getRecordWorkout(workoutId: Int) {
        repo.getWorkoutWithExerciseAnsSets(workoutId)
            .observeUi()
            .doAfterSuccess { dataState ->
                getSelectedFormatter { formatter -> mState.value = dataState.reduce(formatter) }
            }.subscribe()
    }

    private fun showToast(text: String) {
        router.show(Notification.TOAST, ToastBarParams(text))
    }

    private fun getSelectedFormatter(formatter: (DateTimeFormatter) -> Unit) {
        formatterManager.getSelectedFormatter()
            .observeUi()
            .doAfterSuccess { formatterDto -> formatter(formatterDto.formatter) }
            .subscribe()
    }

    override fun triggerIntent(intent: DetailedIntent) {
        return when (intent) {
            is DetailedIntent.GetWorkout -> getRecordWorkout(intent.workoutId)
            is DetailedIntent.ShowToast -> showToast(intent.text)
        }
    }
}
