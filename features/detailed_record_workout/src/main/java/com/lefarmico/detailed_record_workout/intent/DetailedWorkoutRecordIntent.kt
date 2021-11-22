package com.lefarmico.detailed_record_workout.intent

import com.lefarmico.core.base.BaseIntent

sealed class DetailedWorkoutRecordIntent : BaseIntent() {

    data class GetWorkout(val workoutId: Int) : DetailedWorkoutRecordIntent()
}
