package com.lefarmico.core.entity

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

sealed class LibraryViewData : Parcelable {

    @Parcelize
    data class Category(
        val id: Int = 0,
        val title: String = ""
    ) : LibraryViewData()

    @Parcelize
    data class SubCategory(
        val id: Int = 0,
        val title: String = "",
        val categoryId: Int
    ) : LibraryViewData()

    @Parcelize
    data class Exercise(
        val id: Int = 0,
        val title: String,
        val description: String = "",
        val imageRes: String = "",
        val subCategoryId: Int
    ) : LibraryViewData()
}
