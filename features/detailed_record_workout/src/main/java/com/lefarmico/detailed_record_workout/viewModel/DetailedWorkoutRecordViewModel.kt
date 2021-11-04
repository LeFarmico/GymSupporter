package com.lefarmico.detailed_record_workout.viewModel

import androidx.lifecycle.MutableLiveData
import com.lefarmico.core.base.BaseViewModel
import com.lefarmico.core.entity.WorkoutRecordsViewData
import com.lefarmico.core.extensions.observeUi
import com.lefarmico.core.mapper.toViewData
import com.lefarmico.detailed_record_workout.intent.DetailedWorkoutRecordIntent
import com.lefarmico.domain.repository.WorkoutRecordsRepository
import com.lefarmico.domain.utils.DataState
import com.lefarmico.navigation.Router
import javax.inject.Inject

class DetailedWorkoutRecordViewModel @Inject constructor() : BaseViewModel<DetailedWorkoutRecordIntent>() {

    @Inject
    lateinit var repo: WorkoutRecordsRepository
    @Inject
    lateinit var router: Router

    val noteWorkoutLiveData =
        MutableLiveData<DataState<WorkoutRecordsViewData.WorkoutWithExercisesAndSets>>()

    private fun getRecordWorkout(workoutId: Int) {
        repo.getWorkoutWithExerciseAnsSets(workoutId)
            .observeUi()
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

    override fun onTriggerEvent(eventType: DetailedWorkoutRecordIntent) {
        when (eventType) {
            DetailedWorkoutRecordIntent.AddExercise -> TODO()
            is DetailedWorkoutRecordIntent.AddSet -> TODO()
            is DetailedWorkoutRecordIntent.DeleteExercise -> TODO()
            is DetailedWorkoutRecordIntent.DeleteSet -> TODO()
            DetailedWorkoutRecordIntent.Cancel -> TODO()
            DetailedWorkoutRecordIntent.Save -> TODO()
            is DetailedWorkoutRecordIntent.GetWorkout -> getRecordWorkout(eventType.workoutId)
        }
    }
}
