package com.lefarmico.donetime.data.entities.library

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
data class LibraryCategory(
    @PrimaryKey(autoGenerate = true) @ColumnInfo(name = "id") override val id: Int = 0,
    @ColumnInfo(name = "category_title") override val title: String = ""
) : ILibraryItem
