package com.lefarmico.donetime.data

import com.lefarmico.donetime.data.db.dao.ExerciseLibraryDao
import com.lefarmico.donetime.data.entities.library.LibraryCategory
import com.lefarmico.donetime.data.entities.library.LibraryExercise
import com.lefarmico.donetime.data.entities.library.LibrarySubCategory
import io.reactivex.rxjava3.core.Observable

class ExerciseLibraryRepository(private val dao: ExerciseLibraryDao) {

    fun getExerciseCategories(): Observable<List<LibraryCategory>> {
        return dao.getCategories()
    }

    fun getSubCategories(categoryId: Int): Observable<List<LibrarySubCategory>> {
        return dao.getSubCategoriesByCategoryId(categoryId)
    }

    fun getExercises(subCategoryId: Int): Observable<List<LibraryExercise>> {
        return dao.getExercisesBySubCategoryId(subCategoryId)
    }

    fun addCategory(title: String) {
        dao.insertCategory(
            LibraryCategory(
                title = title
            )
        )
    }

    fun addSubCategory(title: String, categoryId: Int) {
        dao.insertSubCategory(
            LibrarySubCategory(
                title = title,
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
