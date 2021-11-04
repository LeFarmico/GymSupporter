package com.lefarmico.detailed_record_workout.intent

import com.lefarmico.core.base.BaseIntent

sealed class DetailedWorkoutRecordIntent : BaseIntent() {

    data class GetWorkout(val workoutId: Int) : DetailedWorkoutRecordIntent()

    data class AddSet(val exerciseId: Int) : DetailedWorkoutRecordIntent()

    data class DeleteSet(val exerciseId: Int) : DetailedWorkoutRecordIntent()

    object AddExercise : DetailedWorkoutRecordIntent()

    data class DeleteExercise(val exerciseId: Int) : DetailedWorkoutRecordIntent()

    object Save : DetailedWorkoutRecordIntent()

    object Cancel : DetailedWorkoutRecordIntent()
}
