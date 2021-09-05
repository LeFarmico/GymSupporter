package com.lefarmico.donetime.intents

import com.lefarmico.donetime.views.base.BaseIntent

sealed class HomeIntent : BaseIntent() {
    
    object GetWorkoutRecords : HomeIntent()
}
