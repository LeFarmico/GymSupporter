package com.lefarmico.donetime.data.entities.library

import com.lefarmico.donetime.data.models.IExerciseLibraryItem

data class ItemLibrarySubCategory(
    override val title: String,
    val id: Int,
    val categoryId: Int
) : IExerciseLibraryItem
