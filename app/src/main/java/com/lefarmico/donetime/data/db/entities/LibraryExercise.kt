package com.lefarmico.donetime.data.db.entities

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.Index
import androidx.room.PrimaryKey

@Entity(
    tableName = "exercise_library_exercise",
    indices = [
        Index(
            value = ["exercise_title"],
            unique = true
        )
    ]
)
data class LibraryExercise(
    @PrimaryKey(autoGenerate = true) @ColumnInfo(name = "id") val id: Int = 0,
    @ColumnInfo(name = "exercise_title") val title: String,
    @ColumnInfo(name = "exercise_description") val description: String = "",
    @ColumnInfo(name = "exercise_image") val image: String?,
    @ColumnInfo(name = "sub_category_id") val subCategoryId: Int,
)
