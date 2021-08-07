package com.lefarmico.donetime.data.db.entities

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.Index
import androidx.room.PrimaryKey

@Entity(
    tableName = "exercise_library_type",
    indices = [
        Index(
            value = ["category_title"],
            unique = true
        )
    ]
)
data class ExerciseLibraryCategory(
    @PrimaryKey(autoGenerate = true) @ColumnInfo(name = "id") val id: Int = 0,
    @ColumnInfo(name = "category_title") val categoryTitle: String = ""
)
