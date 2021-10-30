package com.lefarmico.home.viewModel

import androidx.lifecycle.MutableLiveData
import com.lefarmico.core.base.BaseViewModel
import com.lefarmico.core.entity.WorkoutRecordsViewData
import com.lefarmico.core.mapper.toViewDataWorkoutWithExAndSets
import com.lefarmico.domain.repository.WorkoutRecordsRepository
import com.lefarmico.domain.utils.DataState
import com.lefarmico.home.intent.HomeIntent
import com.lefarmico.navigation.Router
import com.lefarmico.navigation.params.RecordMenuParams
import com.lefarmico.navigation.params.WorkoutScreenParams
import com.lefarmico.navigation.screen.Screen
import javax.inject.Inject

class HomeViewModel @Inject constructor() : BaseViewModel<HomeIntent>() {

    @Inject
    lateinit var repo: WorkoutRecordsRepository
    @Inject
    lateinit var router: Router

    val workoutRecordsLiveData = MutableLiveData<DataState<List<WorkoutRecordsViewData.WorkoutWithExercisesAndSets>>>()

    private fun getWorkoutRecords() {
        repo.getWorkoutsWithExerciseAnsSets()
            .subscribe { dataState ->
                when (dataState) {
                    is DataState.Success -> {
                        val data = dataState.data.toViewDataWorkoutWithExAndSets()
                        val success = DataState.Success(data)
                        workoutRecordsLiveData.postValue(success)
                    }
                    else -> {
                        workoutRecordsLiveData.postValue(
                            dataState
                                as DataState<List<WorkoutRecordsViewData.WorkoutWithExercisesAndSets>>
                        )
                    }
                }
            }
    }

    private fun startWorkoutScreen() {
        router.navigate(
            Screen.WORKOUT_SCREEN,
            WorkoutScreenParams.Empty
        )
    }

    private fun detailsWorkout(workoutId: Int) {
        router.navigate(
            screen = Screen.EDIT_WORKOUT_RECORD_SCREEN,
            data = RecordMenuParams.WorkoutRecord(workoutId)
        )
    }

    private fun removeWorkout(workoutId: Int) {
        repo.deleteWorkoutWithExAndSets(workoutId)
            .doAfterSuccess {
                getWorkoutRecords()
            }
            .subscribe()
    }

    override fun onTriggerEvent(eventType: HomeIntent) {
        when (eventType) {
            HomeIntent.GetWorkoutRecords -> getWorkoutRecords()
            HomeIntent.StartWorkoutScreen -> startWorkoutScreen()
            is HomeIntent.DetailsWorkout -> detailsWorkout(eventType.workoutId)
            is HomeIntent.RemoveWorkout -> removeWorkout(eventType.workoutId)
        }
    }
}
