package com.lefarmico.home.viewModel

import androidx.lifecycle.MutableLiveData
import com.lefarmico.core.base.BaseViewModel
import com.lefarmico.core.entity.WorkoutRecordsViewData
import com.lefarmico.core.extensions.observeUi
import com.lefarmico.core.mapper.toViewDataWorkoutWithExAndSets
import com.lefarmico.core.toolbar.RemoveActionBarEvents
import com.lefarmico.core.utils.SingleLiveEvent
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
    val actionBarLiveData = SingleLiveEvent<RemoveActionBarEvents>()

    private fun getWorkoutRecords() {
        repo.getWorkoutsWithExerciseAnsSets()
            .observeUi()
            .doOnSubscribe { workoutRecordsLiveData.postValue(DataState.Loading) }
            .doAfterSuccess { dataState ->
                when (dataState) {
                    DataState.Empty -> workoutRecordsLiveData.postValue(DataState.Empty)
                    DataState.Loading -> workoutRecordsLiveData.postValue(DataState.Loading)
                    is DataState.Error -> workoutRecordsLiveData.postValue(dataState)
                    is DataState.Success -> {
                        val data = dataState.data.toViewDataWorkoutWithExAndSets()
                        val success = DataState.Success(data)
                        workoutRecordsLiveData.postValue(success)
                    }
                }
            }.subscribe()
    }

    private fun navigateToWorkout() {
        actionBarLiveData.postValue(RemoveActionBarEvents.Close)
        router.navigate(
            screen = Screen.WORKOUT_SCREEN,
            data = WorkoutScreenParams.Empty
        )
    }

    private fun navigateToDetailsWorkout(workoutId: Int) {
        actionBarLiveData.postValue(RemoveActionBarEvents.Close)
        router.navigate(
            screen = Screen.EDIT_WORKOUT_RECORD_SCREEN,
            data = RecordMenuParams.WorkoutRecord(workoutId)
        )
    }

    private fun removeWorkout(workoutId: Int) {
        repo.deleteWorkoutWithExAndSets(workoutId)
            .observeUi()
            .doAfterSuccess { getWorkoutRecords() }
            .subscribe()
    }

    private fun actionBarEvent(actionBarEvent: RemoveActionBarEvents) {
        actionBarLiveData.postValue(actionBarEvent)
    }

    override fun onTriggerEvent(eventType: HomeIntent) {
        when (eventType) {
            HomeIntent.GetWorkoutRecords -> getWorkoutRecords()
            HomeIntent.NavigateToWorkout -> navigateToWorkout()
            is HomeIntent.NavigateToDetailsWorkout -> navigateToDetailsWorkout(eventType.workoutId)
            is HomeIntent.RemoveWorkout -> removeWorkout(eventType.workoutId)
            is HomeIntent.ActionBarEvent -> actionBarEvent(eventType.event)
        }
    }
}
