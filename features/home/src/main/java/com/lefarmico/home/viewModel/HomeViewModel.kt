package com.lefarmico.home.viewModel

import androidx.lifecycle.MutableLiveData
import com.lefarmico.core.base.BaseViewModel
import com.lefarmico.domain.entity.WorkoutRecordsDto
import com.lefarmico.domain.repository.WorkoutRecordsRepository
import com.lefarmico.domain.utils.DataState
import com.lefarmico.home.intent.HomeIntent
import com.lefarmico.navigation.Router
import com.lefarmico.navigation.screen.Screen
import javax.inject.Inject

class HomeViewModel @Inject constructor() : BaseViewModel<HomeIntent>() {

    @Inject
    lateinit var repo: WorkoutRecordsRepository
    @Inject
    lateinit var router: Router

    // TODO поменять Dto на ViewData
    val noteWorkoutLiveData = MutableLiveData<DataState<List<WorkoutRecordsDto.Workout>>>()

    private fun getNoteWorkouts() {
        repo.getWorkouts()
            .subscribe { dataState ->
                noteWorkoutLiveData.postValue(dataState)
            }
    }

    override fun onTriggerEvent(eventType: HomeIntent) {
        when (eventType) {
            HomeIntent.GetWorkoutRecords -> getNoteWorkouts()
            HomeIntent.StartWorkoutScreen -> router.navigate(Screen.WORKOUT_SCREEN)
        }
    }
}
