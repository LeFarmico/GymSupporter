package com.lefarmico.data.db.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.lefarmico.data.db.entity.LibraryData
import io.reactivex.rxjava3.core.Single

@Dao
interface LibraryDao {

    @Query("SELECT * FROM library_category")
    fun getCategories(): Single<List<LibraryData.Category>>

    @Query("SELECT * FROM library_sub_category WHERE category_id = :categoryId")
    fun getSubCategories(categoryId: Int): Single<List<LibraryData.SubCategory>>

    @Query("SELECT * FROM library_exercise WHERE sub_category_id = :subCategoryId")
    fun getExercises(subCategoryId: Int): Single<List<LibraryData.Exercise>>

    @Query("SELECT * FROM library_exercise WHERE id = :exerciseId")
    fun getExercise(exerciseId: Int): Single<LibraryData.Exercise>

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    fun insertCategory(category: LibraryData.Category): Long

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    fun insertSubCategory(subCategory: LibraryData.SubCategory): Long

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    fun insertExercise(exercise: LibraryData.Exercise): Long
}
