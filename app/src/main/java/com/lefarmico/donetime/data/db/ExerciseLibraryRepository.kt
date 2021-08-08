package com.lefarmico.donetime.data.db

import com.lefarmico.donetime.data.db.dao.ExerciseLibraryDao
import com.lefarmico.donetime.data.db.entities.LibraryCategory
import com.lefarmico.donetime.data.db.entities.LibraryExercise
import com.lefarmico.donetime.data.db.entities.LibrarySubCategory

class ExerciseLibraryRepository(private val dao: ExerciseLibraryDao) {

    fun getExerciseCategories(): List<LibraryCategory> {
        return dao.getCategories()
    }

    fun getSubCategories(categoryId: Int): List<LibrarySubCategory> {
        return dao.getSubCategoriesByCategoryId(categoryId)
    }

    fun getExercises(subCategoryId: Int): List<LibraryExercise> {
        return dao.getExercisesBySubCategoryId(subCategoryId)
    }

    fun addCategory(title: String) {
        dao.insertCategory(
            LibraryCategory(
                categoryTitle = title
            )
        )
    }

    fun addSubCategory(title: String, categoryId: Int) {
        dao.insertSubCategory(
            LibrarySubCategory(
                subCategory = title,
                categoryId = categoryId
            )
        )
    }

    fun addExercise(title: String, description: String, imageRes: String?, subCategoryId: Int) {
        dao.insertExercise(
            LibraryExercise(
                title = title,
                description = description,
                image = imageRes ?: "",
                subCategoryId = subCategoryId
            )
        )
    }
}
