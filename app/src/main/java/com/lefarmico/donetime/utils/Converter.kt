package com.lefarmico.donetime.utils

import com.lefarmico.donetime.data.db.entities.LibraryCategory
import com.lefarmico.donetime.data.db.entities.LibraryExercise
import com.lefarmico.donetime.data.db.entities.LibrarySubCategory
import com.lefarmico.donetime.data.entities.library.ItemLibraryCategory
import com.lefarmico.donetime.data.entities.library.ItemLibraryExercise
import com.lefarmico.donetime.data.entities.library.ItemLibrarySubCategory

object Converter {

    fun convertLibraryCategoryToItemCategory(categoryDao: LibraryCategory): ItemLibraryCategory {
        return ItemLibraryCategory(
            title = categoryDao.categoryTitle
        )
    }

    fun convertLibrarySubCategoryToItemSubCategory(subCategory: LibrarySubCategory): ItemLibrarySubCategory {
        return ItemLibrarySubCategory(
            title = subCategory.subCategory
        )
    }

    fun convertLibraryExerciseToItemExercise(exercise: LibraryExercise): ItemLibraryExercise {
        return ItemLibraryExercise(
            title = exercise.title,
            description = exercise.description,
            image = exercise.image
        )
    }
}