package com.lefarmico.home.viewModel

import androidx.lifecycle.MutableLiveData
import com.lefarmico.core.base.BaseViewModel
import com.lefarmico.domain.entity.WorkoutRecordsDto
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

    val workoutRecordsLiveData = MutableLiveData<DataState<List<WorkoutRecordsDto.WorkoutWithExercisesAndSets>>>()

    private fun getWorkoutRecords() {
        repo.getWorkoutsWithExerciseAnsSets()
            .subscribe { dataState ->
                workoutRecordsLiveData.postValue(dataState)
            }
    }

    private fun startWorkoutScreen() {
        router.navigate(
            Screen.WORKOUT_SCREEN,
            WorkoutScreenParams.Empty
        )
    }

    private fun editWorkout(workoutId: Int) {
        router.navigate(
            screen = Screen.EDIT_WORKOUT_RECORD_SCREEN,
            data = RecordMenuParams.WorkoutRecord(workoutId)
        )
    }

    override fun onTriggerEvent(eventType: HomeIntent) {
        when (eventType) {
            HomeIntent.GetWorkoutRecords -> getWorkoutRecords()
            HomeIntent.StartWorkoutScreen -> startWorkoutScreen()
            is HomeIntent.EditWorkout -> editWorkout(eventType.workoutId)
        }
    }
}
