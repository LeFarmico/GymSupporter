package com.lefarmico.data.repository

import com.lefarmico.data.db.dao.LibraryDao
import com.lefarmico.data.extensions.dataStateResolver
import com.lefarmico.data.mapper.toData
import com.lefarmico.data.mapper.toDto
import com.lefarmico.domain.entity.LibraryDto
import com.lefarmico.domain.repository.LibraryRepository
import com.lefarmico.domain.utils.DataState
import io.reactivex.rxjava3.core.Single
import javax.inject.Inject

class LibraryRepositoryImpl @Inject constructor(
    private val dao: LibraryDao
) : LibraryRepository {

    override fun getCategories(): Single<DataState<List<LibraryDto.Category>>> {
        return dao.getCategories().map { data -> dataStateResolver { data.toDto() } }
    }

    override fun getCategory(categoryId: Int): Single<DataState<LibraryDto.Category>> {
        return dao.getCategory(categoryId).map { data -> dataStateResolver { data.toDto() } }
    }

    override fun getSubCategories(categoryId: Int): Single<DataState<List<LibraryDto.SubCategory>>> {
        return dao.getSubCategories(categoryId).map { data -> dataStateResolver { data.toDto() } }
    }

    override fun getSubCategory(subcategoryId: Int): Single<DataState<LibraryDto.SubCategory>> {
        return dao.getSubcategory(subcategoryId).map { data -> dataStateResolver { data.toDto() } }
    }

    override fun getExercises(subCategoryId: Int): Single<DataState<List<LibraryDto.Exercise>>> {
        return dao.getExercises(subCategoryId).map { data -> dataStateResolver { data.toDto() } }
    }

    override fun getExercise(exerciseId: Int): Single<DataState<LibraryDto.Exercise>> {
        return dao.getExercise(exerciseId).map { data -> dataStateResolver { data.toDto() } }
    }

    override fun addCategory(category: LibraryDto.Category): Single<DataState<Long>> {
        return dao.insertCategory(category.toData()).map { dataStateResolver { it } }
    }

    override fun addSubCategory(subCategory: LibraryDto.SubCategory): Single<DataState<Long>> {
        return dao.insertSubCategory(subCategory.toData()).map { dataStateResolver { it } }
    }

    override fun addExercise(exercise: LibraryDto.Exercise): Single<DataState<Long>> {
        return dao.insertExercise(exercise.toData()).map { dataStateResolver { it } }
    }

    override fun deleteCategory(categoryId: Int): Single<DataState<Int>> {
        return dao.deleteCategory(categoryId).map { dataStateResolver { it } }
    }

    override fun deleteSubcategory(subCategoryId: Int): Single<DataState<Int>> {
        return dao.deleteSubcategory(subCategoryId).map { dataStateResolver { it } }
    }

    override fun deleteExercise(exerciseId: Int): Single<DataState<Int>> {
        return dao.deleteExercise(exerciseId).map { dataStateResolver { it } }
    }
}
