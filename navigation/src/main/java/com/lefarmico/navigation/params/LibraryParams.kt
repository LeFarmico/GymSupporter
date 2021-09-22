package com.lefarmico.navigation.params

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

sealed class LibraryParams : Parcelable {

    @Parcelize
    data class CategoryList(
        val isFromWorkoutScreen: Boolean
    ) : LibraryParams()

    @Parcelize
    data class SubcategoryList(
        val categoryId: Int,
        val isFromWorkoutScreen: Boolean
    ) : LibraryParams()

    @Parcelize
    data class ExerciseList(
        val categoryId: Int,
        val subCategoryId: Int,
        val isFromWorkoutScreen: Boolean
    ) : LibraryParams()
}
