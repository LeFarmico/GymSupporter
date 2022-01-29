package com.lefarmico.data.db.dao

import androidx.room.*
import com.lefarmico.data.db.entity.LibraryData
import io.reactivex.rxjava3.core.Single

@Dao
interface LibraryDao {

    @Query("SELECT * FROM library_category")
    fun getCategories(): Single<List<LibraryData.Category>>

    @Query("SELECT * FROM library_category WHERE id = :categoryId")
    fun getCategory(categoryId: Int): Single<LibraryData.Category>

    @Query("SELECT * FROM library_sub_category WHERE category_id = :categoryId")
    fun getSubCategories(categoryId: Int): Single<List<LibraryData.SubCategory>>

    @Query("SELECT * FROM library_sub_category WHERE id = :subcategoryId")
    fun getSubcategory(subcategoryId: Int): Single<LibraryData.SubCategory>

    @Query("SELECT * FROM library_exercise WHERE sub_category_id = :subCategoryId")
    fun getExercises(subCategoryId: Int): Single<List<LibraryData.Exercise>>

    @Query("SELECT * FROM library_exercise WHERE id = :exerciseId")
    fun getExercise(exerciseId: Int): Single<LibraryData.Exercise>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertCategory(category: LibraryData.Category): Single<Long>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertSubCategory(subCategory: LibraryData.SubCategory): Single<Long>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertSubCategories(subCategoryList: List<LibraryData.SubCategory>): Single<List<Long>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertExercise(exercise: LibraryData.Exercise): Single<Long>

    @Query("DELETE FROM library_category WHERE id = :categoryId")
    fun deleteCategory(categoryId: Int): Single<Int>

    @Query("DELETE FROM library_sub_category WHERE id = :subCategoryId")
    fun deleteSubcategory(subCategoryId: Int): Single<Int>

    @Query("DELETE FROM library_exercise WHERE id = :exerciseId")
    fun deleteExercise(exerciseId: Int): Single<Int>
}
