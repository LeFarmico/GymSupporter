package com.lefarmico.donetime.data.entities.currentExercise

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class ExerciseName(
    val name: String,
    val tags: String,
) : Parcelable
