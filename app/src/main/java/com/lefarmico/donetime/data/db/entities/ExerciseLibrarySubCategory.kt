package com.lefarmico.donetime.data.db.entities

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
data class ExerciseLibrarySubCategory(
    @PrimaryKey(autoGenerate = true) @ColumnInfo(name = "id") val id: Int = 0,
    @ColumnInfo(name = "sub_category_title") val subCategory: String = "",
    @ColumnInfo(name = "category_id") val categoryId: Int,
)
