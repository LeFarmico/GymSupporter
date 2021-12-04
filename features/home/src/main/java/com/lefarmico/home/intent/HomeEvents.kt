package com.lefarmico.home.intent

sealed class HomeEvents {
    object TurnNextMonth : HomeEvents()

    object TurnPrevMonth : HomeEvents()

    object StartNewWorkout : HomeEvents()

    object SelectAllWorkouts : HomeEvents()

    object DeleteSelectedWorkouts : HomeEvents()

    object ShowEditState : HomeEvents()

    object HideEditState : HomeEvents()
}
