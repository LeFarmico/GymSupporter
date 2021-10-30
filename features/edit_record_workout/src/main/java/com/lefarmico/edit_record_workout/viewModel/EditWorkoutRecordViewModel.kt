package com.lefarmico.edit_record_workout.viewModel

import androidx.lifecycle.MutableLiveData
import com.lefarmico.core.base.BaseViewModel
import com.lefarmico.core.entity.WorkoutRecordsViewData
import com.lefarmico.core.mapper.toViewData
import com.lefarmico.domain.repository.WorkoutRecordsRepository
import com.lefarmico.domain.utils.DataState
import com.lefarmico.edit_record_workout.intent.EditWorkoutRecordIntent
import com.lefarmico.navigation.Router
import javax.inject.Inject

class EditWorkoutRecordViewModel @Inject constructor() : BaseViewModel<EditWorkoutRecordIntent>() {

    @Inject
    lateinit var repo: WorkoutRecordsRepository
    @Inject
    lateinit var router: Router

    val noteWorkoutLiveData =
        MutableLiveData<DataState<WorkoutRecordsViewData.WorkoutWithExercisesAndSets>>()

    private fun getRecordWorkout(workoutId: Int) {
        repo.getWorkoutWithExerciseAnsSets(workoutId)
            .subscribe { dataState ->
                when (dataState) {
                    is DataState.Success -> {
                        noteWorkoutLiveData.postValue(DataState.Success(dataState.data.toViewData()))
                    }
                    else -> {
                        noteWorkoutLiveData.postValue(
                            dataState as DataState<WorkoutRecordsViewData.WorkoutWithExercisesAndSets>
                        )
                    }
                }
            }
    }

    override fun onTriggerEvent(eventType: EditWorkoutRecordIntent) {
        when (eventType) {
            EditWorkoutRecordIntent.AddExercise -> TODO()
            is EditWorkoutRecordIntent.AddSet -> TODO()
            is EditWorkoutRecordIntent.DeleteExercise -> TODO()
            is EditWorkoutRecordIntent.DeleteSet -> TODO()
            EditWorkoutRecordIntent.Cancel -> TODO()
            EditWorkoutRecordIntent.Save -> TODO()
            is EditWorkoutRecordIntent.GetWorkout -> getRecordWorkout(eventType.workoutId)
        }
    }
}
