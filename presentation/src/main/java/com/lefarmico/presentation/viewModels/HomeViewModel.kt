package com.lefarmico.donetime.viewModels

import androidx.lifecycle.MutableLiveData
import com.lefarmico.donetime.App
import com.lefarmico.donetime.data.Interactor
import com.lefarmico.donetime.data.entities.note.NoteWorkout
import com.lefarmico.donetime.views.base.BaseViewModel
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers
import io.reactivex.rxjava3.schedulers.Schedulers
import javax.inject.Inject

class HomeViewModel : BaseViewModel() {

    @Inject lateinit var interactor: Interactor

    val noteWorkoutLiveData = MutableLiveData<List<NoteWorkout>>()

    init {
        App.appComponent.inject(this)
        getNoteWorkouts()
    }

    private fun getNoteWorkouts() {
        interactor.getNoteWorkoutsFromDB()
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe {
                noteWorkoutLiveData.postValue(it)
            }
    }
}
