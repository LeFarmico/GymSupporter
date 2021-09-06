package com.lefarmico.presentation.intents

import com.lefarmico.presentation.views.base.BaseIntent

sealed class HomeIntent : BaseIntent() {
    
    object GetWorkoutRecords : HomeIntent()
}
