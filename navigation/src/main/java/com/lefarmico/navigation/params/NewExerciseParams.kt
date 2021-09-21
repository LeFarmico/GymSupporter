package com.lefarmico.navigation.params

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

sealed class NewExerciseParams : Parcelable {

    @Parcelize
    data class Exercise(
        val id: Int,
        val categoryId: Int,
        val subcategoryId: Int
    ) : NewExerciseParams()
}
