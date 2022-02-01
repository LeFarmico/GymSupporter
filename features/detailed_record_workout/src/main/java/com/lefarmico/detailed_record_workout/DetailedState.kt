package com.lefarmico.detailed_record_workout

import com.lefarmico.core.base.BaseState
import com.lefarmico.core.entity.WorkoutRecordsViewData
import java.lang.Exception

sealed class DetailedState : BaseState.State {
    data class WorkoutResult(
        val workout: WorkoutRecordsViewData.Workout,
        val exercises: List<WorkoutRecordsViewData.ViewDataItemType>
    ) : DetailedState()
    data class DateResult(val dateText: String) : DetailedState()
    data class TitleResult(val title: String) : DetailedState()
}
