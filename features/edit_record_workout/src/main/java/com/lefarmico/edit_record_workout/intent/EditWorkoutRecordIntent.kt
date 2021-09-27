package com.lefarmico.edit_record_workout.intent

import com.lefarmico.core.base.BaseIntent

sealed class EditWorkoutRecordIntent : BaseIntent() {

    data class GetWorkout(val workoutId: Int) : EditWorkoutRecordIntent()

    data class AddSet(val exerciseId: Int) : EditWorkoutRecordIntent()

    data class DeleteSet(val exerciseId: Int) : EditWorkoutRecordIntent()

    object AddExercise : EditWorkoutRecordIntent()

    data class DeleteExercise(val exerciseId: Int) : EditWorkoutRecordIntent()

    object Save : EditWorkoutRecordIntent()

    object Cancel : EditWorkoutRecordIntent()
}
