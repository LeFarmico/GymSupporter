package com.lefarmico.detailed_record_workout

import com.lefarmico.core.base.BaseState
import com.lefarmico.core.entity.WorkoutRecordsViewData
import java.lang.Exception

sealed class DetailedState : BaseState.State {
    data class WorkoutResult(
        val workout: WorkoutRecordsViewData.WorkoutWithExercisesAndSets
    ) : DetailedState()
    object Loading : DetailedState()
    data class ExceptionResult(val exception: Exception) : DetailedState()
    data class DateResult(val dateText: String) : DetailedState()
    data class TitleResult(val title: String) : DetailedState()
}
