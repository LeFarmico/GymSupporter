package com.lefarmico.core.entity

sealed class LibraryViewData {

    data class Category(
        val id: Int = 0,
        val title: String = ""
    ) : LibraryViewData()

    data class SubCategory(
        val id: Int = 0,
        val title: String = "",
        val categoryId: Int
    ) : LibraryViewData()

    data class Exercise(
        val id: Int = 0,
        val title: String,
        val description: String = "",
        val imageRes: String = "",
        val subCategoryId: Int
    ) : LibraryViewData()
}
