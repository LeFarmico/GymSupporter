package com.lefarmico.presentation.viewModels

import androidx.lifecycle.MutableLiveData
import com.lefarmico.domain.entity.WorkoutRecordsDto
import com.lefarmico.domain.repository.WorkoutRecordsRepository
import com.lefarmico.domain.utils.DataState
import com.lefarmico.presentation.intents.HomeIntent
import com.lefarmico.presentation.views.base.BaseViewModel
import javax.inject.Inject

class HomeViewModel @Inject constructor() : BaseViewModel<HomeIntent>() {

    @Inject lateinit var repo: WorkoutRecordsRepository

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
        }
    }
}
