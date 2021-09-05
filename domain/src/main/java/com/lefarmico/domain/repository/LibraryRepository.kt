package com.lefarmico.domain.repository

import com.lefarmico.domain.entity.LibraryDto
import com.lefarmico.domain.utils.DataState
import io.reactivex.rxjava3.core.Observable
import io.reactivex.rxjava3.core.Single

interface LibraryRepository : BaseRepository {

    fun getCategories(): Observable<DataState<List<LibraryDto.Category>>>

    fun getSubCategories(categoryId: Int): Observable<DataState<List<LibraryDto.SubCategory>>>

    fun getExercises(subCategoryId: Int): Observable<DataState<List<LibraryDto.Exercise>>>
    
    fun getExercise(exerciseId: Int): Observable<DataState<LibraryDto.Exercise>>

    fun addCategory(category: LibraryDto.Category): Single<DataState<Long>>

    fun addSubCategory(subCategory: LibraryDto.SubCategory): Single<DataState<Long>>

    fun addExercise(exercise: LibraryDto.Exercise): Single<DataState<Long>>
}
