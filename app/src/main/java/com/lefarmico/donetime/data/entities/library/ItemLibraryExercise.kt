package com.lefarmico.donetime.data.entities.library

import com.lefarmico.donetime.data.models.IExerciseLibraryItem

data class ItemLibraryExercise(
    override val title: String,
    val description: String,
    val image: String?,
    val id: Int,
    val subCategoryId: Int
) : IExerciseLibraryItem
