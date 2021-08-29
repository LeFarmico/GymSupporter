package com.lefarmico.donetime.data

import com.lefarmico.donetime.data.db.dao.ExerciseLibraryDao
import com.lefarmico.donetime.data.entities.library.ILibraryItem
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
        val observable = dao.getCategories().map { it as List<ILibraryItem> }
        val isUnique = checkUnique(title, observable)
        if (isUnique) {
            dao.insertCategory(
                LibraryCategory(
                    title = title
                )
            )
        }
    }

    fun addSubCategory(title: String, categoryId: Int) {
        val observable = dao.getCategories().map { it as List<ILibraryItem> }
        val isUnique = checkUnique(title, observable)
        if (isUnique) {
            dao.insertSubCategory(
                LibrarySubCategory(
                    title = title,
                    categoryId = categoryId
                )
            )
        }
    }

    fun addExercise(title: String, description: String, imageRes: String?, subCategoryId: Int) {
        val observable = dao.getCategories().map { it as List<ILibraryItem> }
        val isUnique = checkUnique(title, observable)
        if (isUnique) {
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

    fun addExercise(exercise: LibraryExercise) {
        val observable = dao.getCategories().map { it as List<ILibraryItem> }
        val isUnique = checkUnique(exercise.title, observable)
        if (isUnique) {
            dao.insertExercise(exercise)
        }
    }

    private fun checkUnique(title: String, itemsListObservable: Observable<List<ILibraryItem>>): Boolean {
        var isUnique = true
        itemsListObservable.subscribe {
            isUnique = !it.any { category ->
                category.title == title
            }
        }
        return isUnique
    }
}
