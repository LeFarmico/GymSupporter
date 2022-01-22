package com.lefarmico.workout

import com.lefarmico.core.base.BaseIntent
import com.lefarmico.navigation.params.SetParameterParams
import java.time.LocalDate
import java.time.LocalTime

sealed class WorkoutIntent : BaseIntent {

    sealed class ExSet : WorkoutIntent() {
        data class AddExSet(val params: SetParameterParams) : ExSet()
        data class DeleteLastExSet(val exerciseId: Int) : ExSet()
    }

    data class CloseWorkout(val workoutId: Int) : WorkoutIntent()

    data class ShowToast(val text: String) : WorkoutIntent()

    sealed class Navigate : WorkoutIntent() {
        data class ExerciseDetails(val exLibId: Int) : Navigate()
        object CategoryMenu : Navigate()
        object Home : Navigate()
    }

    sealed class Dialog : WorkoutIntent() {
        data class SetParamsDialog(val exerciseId: Int) : Dialog()
        object CalendarDialog : Dialog()
        object TitleDialog : Dialog()
        object TimeDialog : Dialog()
    }
    sealed class Date : WorkoutIntent() {
        object Get : Date()
        data class Set(val date: LocalDate) : Date()
    }

    sealed class Title : WorkoutIntent() {
        object Get : Title()
        data class Set(val title: String) : Title()
    }

    sealed class Exercise : WorkoutIntent() {
        data class Add(val id: Int) : Exercise()
        data class Delete(val id: Int) : Exercise()
    }

    sealed class Time : WorkoutIntent() {
        object Get : Time()
        data class Set(val time: LocalTime) : Time()
    }

    sealed class SwitchState : WorkoutIntent() {
        object Get : SwitchState()
        data class Set(val state: Boolean) : SwitchState()
    }

    sealed class EditState : WorkoutIntent() {
        object Show : EditState()
        object Hide : EditState()
        object SelectAll : EditState()
        object DeselectAll : EditState()
        object DeleteSelected : EditState()
    }

    sealed class Workout : WorkoutIntent() {
        object New : Workout()
        data class Load(val workoutRecordId: Int) : Workout()
        object Finish : Workout()
    }
}
