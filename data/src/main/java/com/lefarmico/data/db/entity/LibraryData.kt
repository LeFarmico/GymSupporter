package com.lefarmico.data.db.entity

import androidx.room.* // ktlint-disable no-wildcard-imports

sealed class LibraryData {

    @Entity(
        tableName = "library_category",
        indices = [
            Index(
                value = ["title"],
                unique = true
            )
        ]
    )
    data class Category(
        @PrimaryKey(autoGenerate = true) @ColumnInfo(name = "id") val id: Int,
        @ColumnInfo(name = "title") val title: String
    ) : LibraryData()

    @Entity(tableName = "library_sub_category")
    data class SubCategory(
        @PrimaryKey(autoGenerate = true) @ColumnInfo(name = "id") val id: Int = 0,
        @ColumnInfo(name = "title") val title: String,
        @ColumnInfo(name = "category_id") val categoryId: Int
    ) : LibraryData()

    @Entity(tableName = "library_exercise")
    data class Exercise(
        @PrimaryKey(autoGenerate = true) @ColumnInfo(name = "id") val id: Int = 0,
        @ColumnInfo(name = "title") val title: String,
        @ColumnInfo(name = "description") val description: String = "",
        @ColumnInfo(name = "image_res") val imageRes: String = "",
        @ColumnInfo(name = "sub_category_id") val subCategoryId: Int
    ) : LibraryData()
}
