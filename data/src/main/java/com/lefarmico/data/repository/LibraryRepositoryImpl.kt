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
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers
import io.reactivex.rxjava3.core.Observable
import io.reactivex.rxjava3.core.Single
import io.reactivex.rxjava3.schedulers.Schedulers
import java.lang.Exception
import javax.inject.Inject

class LibraryRepositoryImpl @Inject constructor(
    private val dao: LibraryDao
) : LibraryRepository {

    override fun getCategories(): Observable<DataState<List<LibraryDto.Category>>> {
        return dao.getCategories()
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
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

    override fun getSubCategories(categoryId: Int): Observable<DataState<List<LibraryDto.SubCategory>>> {
        return dao.getSubCategories(categoryId)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
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

    override fun getExercises(subCategoryId: Int): Observable<DataState<List<LibraryDto.Exercise>>> {
        return dao.getExercises(subCategoryId)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
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

    override fun getExercise(exerciseId: Int): Observable<DataState<LibraryDto.Exercise>> {
        return dao.getExercise(exerciseId)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .map { data ->
                DataState.Success(data.toDto()) as DataState<LibraryDto.Exercise>
            }
            .onErrorReturn {
                DataState.Error(it as Exception)
            }
    }

    override fun addCategory(category: LibraryDto.Category): Single<DataState<Long>> {
        return Single.create<DataState<Long>> {
            val data = dao.insertCategory(category.toData())
            it.onSuccess(DataState.Success(data))
        }
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .onErrorReturn {
                DataState.Error(it as Exception)
            }
    }

    override fun addSubCategory(subCategory: LibraryDto.SubCategory): Single<DataState<Long>> {
        return Single.create<DataState<Long>> {
            val data = dao.insertSubCategory(subCategory.toData())
            it.onSuccess(DataState.Success(data))
        }
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .onErrorReturn {
                DataState.Error(it as Exception)
            }
    }

    override fun addExercise(exercise: LibraryDto.Exercise): Single<DataState<Long>> {
        return Single.create<DataState<Long>> {
            val data = dao.insertExercise(exercise.toData())
            it.onSuccess(DataState.Success(data))
        }
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .onErrorReturn {
                DataState.Error(it as Exception)
            }
    }
}
