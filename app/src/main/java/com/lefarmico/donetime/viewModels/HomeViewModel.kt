package com.lefarmico.donetime.viewModels

import androidx.lifecycle.MutableLiveData
import com.lefarmico.data.repository.WorkoutRecordsRepositoryImpl
import com.lefarmico.domain.entity.WorkoutRecordsDto
import com.lefarmico.domain.utils.DataState
import com.lefarmico.donetime.App
import com.lefarmico.donetime.intents.HomeIntent
import com.lefarmico.donetime.views.base.BaseViewModel
import javax.inject.Inject

class HomeViewModel : BaseViewModel<HomeIntent>() {

    @Inject lateinit var repo: WorkoutRecordsRepositoryImpl

    val noteWorkoutLiveData = MutableLiveData<DataState<List<WorkoutRecordsDto.Workout>>>()

    init {
        App.appComponent.inject(this)
    }

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
