package com.lefarmico.donetime.data.entities.library

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.Index
import androidx.room.PrimaryKey

@Entity(
    tableName = "exercise_library_sub_category",
    indices = [
        Index(
            value = ["sub_category_title"],
            unique = true
        )
    ]
)
data class LibrarySubCategory(
    @PrimaryKey(autoGenerate = true) @ColumnInfo(name = "id") override val id: Int = 0,
    @ColumnInfo(name = "sub_category_title") override val title: String = "",
    @ColumnInfo(name = "category_id") val categoryId: Int,
) : ILibraryItem
