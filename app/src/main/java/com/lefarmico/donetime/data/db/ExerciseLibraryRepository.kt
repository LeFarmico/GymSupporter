package com.lefarmico.donetime.data.db

import com.lefarmico.donetime.data.db.dao.ExerciseLibraryDao
import com.lefarmico.donetime.data.db.entities.ExerciseLibraryCategory
import com.lefarmico.donetime.data.db.entities.ExerciseLibraryExercise
import com.lefarmico.donetime.data.db.entities.ExerciseLibrarySubCategory

class ExerciseLibraryRepository(private val dao: ExerciseLibraryDao) {

    fun getExerciseCategories(): List<ExerciseLibraryCategory> {
        return dao.getCategories()
    }

    fun getSubCategories(categoryId: Int): List<ExerciseLibrarySubCategory> {
        return dao.getSubCategoriesByCategoryId(categoryId)
    }

    fun getExercises(subCategoryId: Int): List<ExerciseLibraryExercise> {
        return dao.getExercisesBySubCategoryId(subCategoryId)
    }

    fun addCategory(title: String) {
        dao.insertCategory(
            ExerciseLibraryCategory(
                categoryTitle = title
            )
        )
    }

    fun addSubCategory(title: String, categoryId: Int) {
        dao.insertSubCategory(
            ExerciseLibrarySubCategory(
                subCategory = title,
                categoryId = categoryId
            )
        )
    }

    fun addExercise(title: String, description: String, imageRes: String?, subCategoryId: Int) {
        dao.insertExercise(
            ExerciseLibraryExercise(
                title = title,
                description = description,
                image = imageRes ?: "",
                subCategoryId = subCategoryId
            )
        )
    }
}
