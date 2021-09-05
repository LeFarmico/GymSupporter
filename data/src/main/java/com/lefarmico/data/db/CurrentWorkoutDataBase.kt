package com.lefarmico.data.db

import com.lefarmico.domain.entity.WorkoutRecordsDto

class CurrentWorkoutDataBase {

    val exerciseList = mutableListOf<WorkoutRecordsDto.Exercise>()
    
    companion object {
        const val LOADING = 2L
        const val SUCCESS = 1L
        const val EMPTY = 0L
        const val ERROR = -1L
    }
}
