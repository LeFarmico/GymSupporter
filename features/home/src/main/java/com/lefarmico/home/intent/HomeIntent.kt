package com.lefarmico.home.intent

import com.lefarmico.core.base.BaseIntent

sealed class HomeIntent : BaseIntent() {

    object GetWorkoutRecords : HomeIntent()
}
