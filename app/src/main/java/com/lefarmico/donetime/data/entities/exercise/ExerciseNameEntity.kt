package com.lefarmico.donetime.data.entities.exercise

import android.os.Parcelable
import com.lefarmico.donetime.data.models.ICurrentExerciseItem
import kotlinx.parcelize.Parcelize

@Parcelize
data class ExerciseNameEntity(
    val name: String,
    val tags: String,
) : Parcelable, ICurrentExerciseItem
