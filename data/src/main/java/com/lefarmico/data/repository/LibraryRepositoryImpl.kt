package com.lefarmico.data.repository

import com.lefarmico.data.db.dao.LibraryDao
import com.lefarmico.data.mapper.toData
import com.lefarmico.data.mapper.toDto
import com.lefarmico.data.mapper.toDtoCategoryList
import com.lefarmico.data.mapper.toDtoExerciseList
import com.lefarmico.data.mapper.toDtoSubCategoryList
import com.lefarmico.domain.entity.LibraryDto
import com.lefarmico.domain.repository.LibraryRepository
import com.lefarmico.domain.utils.DataState
import io.reactivex.rxjava3.core.Single
import java.lang.Exception
import javax.inject.Inject

class LibraryRepositoryImpl @Inject constructor(
    private val dao: LibraryDao
) : LibraryRepository {

    override fun getCategories(): Single<DataState<List<LibraryDto.Category>>> {
        return dao.getCategories()
            .map { data ->
                if (data.isNotEmpty()) {
                    DataState.Success(data.toDtoCategoryList())
                } else {
                    DataState.Empty
                }
            }
            .onErrorReturn {
                DataState.Error(it as Exception)
            }
    }

    override fun getSubCategories(categoryId: Int): Single<DataState<List<LibraryDto.SubCategory>>> {
        return dao.getSubCategories(categoryId)
            .map { data ->
                if (data.isNotEmpty()) {
                    DataState.Success(data.toDtoSubCategoryList())
                } else {
                    DataState.Empty
                }
            }
            .onErrorReturn {
                DataState.Error(it as Exception)
            }
    }

    override fun getExercises(subCategoryId: Int): Single<DataState<List<LibraryDto.Exercise>>> {
        return dao.getExercises(subCategoryId)
            .map { data ->
                if (data.isNotEmpty()) {
                    DataState.Success(data.toDtoExerciseList())
                } else {
                    DataState.Empty
                }
            }
            .onErrorReturn {
                DataState.Error(it as Exception)
            }
    }

    override fun getExercise(exerciseId: Int): Single<DataState<LibraryDto.Exercise>> {
        return dao.getExercise(exerciseId)
            .map { data ->
                DataState.Success(data.toDto()) as DataState<LibraryDto.Exercise>
            }
            .onErrorReturn { err ->
                DataState.Error(err as Exception)
            }
    }

    override fun addCategory(category: LibraryDto.Category): Single<DataState<Long>> {
        return Single.create<DataState<Long>> {
            val data = dao.insertCategory(category.toData())
            it.onSuccess(DataState.Success(data))
        }
            .onErrorReturn {
                DataState.Error(it as Exception)
            }
    }

    override fun addSubCategory(subCategory: LibraryDto.SubCategory): Single<DataState<Long>> {
        return Single.create<DataState<Long>> {
            val data = dao.insertSubCategory(subCategory.toData())
            it.onSuccess(DataState.Success(data))
        }
            .onErrorReturn {
                DataState.Error(it as Exception)
            }
    }

    override fun addExercise(exercise: LibraryDto.Exercise): Single<DataState<Long>> {
        return Single.create<DataState<Long>> {
            val data = dao.insertExercise(exercise.toData())
            it.onSuccess(DataState.Success(data))
        }
            .onErrorReturn {
                DataState.Error(it as Exception)
            }
    }
}
