package com.lefarmico.donetime.data.entities.library

import com.lefarmico.donetime.data.models.IExerciseLibraryItem

data class ItemLibraryCategory(
    override val title: String,
    val id: Int
) : IExerciseLibraryItem
