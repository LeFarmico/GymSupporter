package com.lefarmico.navigation.params

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

sealed class LibraryParams : Parcelable {

    @Parcelize
    data class Category(
        val id: Int,
        val isFromWorkoutScreen: Boolean
    ) : LibraryParams()

    @Parcelize
    data class Subcategory(
        val id: Int,
        val categoryId: Int,
        val isFromWorkoutScreen: Boolean
    ) : LibraryParams()

    @Parcelize
    data class Exercise(
        val id: Int,
        val categoryId: Int,
        val subCategoryId: Int,
        val isFromWorkoutScreen: Boolean
    ) : LibraryParams()
}
