package com.lefarmico.navigation.params

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

sealed class RecordMenuParams : Parcelable {

    @Parcelize
    data class WorkoutRecord(
        val workoutId: Int
    ) : RecordMenuParams()
}
