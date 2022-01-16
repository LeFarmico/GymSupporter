package com.lefarmico.workout

import com.lefarmico.core.base.BaseState
import com.lefarmico.core.entity.CurrentWorkoutViewData
import java.lang.Exception

sealed class WorkoutState : BaseState.State {
    data class ExerciseResult(
        val exerciseList: List<CurrentWorkoutViewData.ExerciseWithSets>
    ) : WorkoutState()
    data class DateResult(val date: String) : WorkoutState()
    data class TitleResult(val title: String) : WorkoutState()
    data class ExceptionResult(val exception: Exception) : WorkoutState()
    data class TimeResult(val time: String) : WorkoutState()
    data class SwitchState(val isOn: Boolean) : WorkoutState()
    data class EndWorkoutResult(val workoutId: Long) : WorkoutState()
    object Loading : WorkoutState()
}
