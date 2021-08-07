package com.lefarmico.donetime.data.db.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.lefarmico.donetime.data.db.entities.ExerciseLibraryCategory
import com.lefarmico.donetime.data.db.entities.ExerciseLibraryExercise
import com.lefarmico.donetime.data.db.entities.ExerciseLibrarySubCategory

@Dao
interface ExerciseLibraryDao {

    @Query("SELECT * FROM exercise_library_type")
    fun getCategories(): List<ExerciseLibraryCategory>

    @Query("SELECT * FROM exercise_library_sub_category WHERE category_id = :id")
    fun getSubCategoriesByCategoryId(id: Int): List<ExerciseLibrarySubCategory>

    @Query("SELECT * FROM exercise_library_exercise WHERE sub_category_id = :id")
    fun getExercisesBySubCategoryId(id: Int): List<ExerciseLibraryExercise>

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    fun insertCategory(category: ExerciseLibraryCategory)

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    fun insertSubCategory(subCategory: ExerciseLibrarySubCategory)

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    fun insertExercise(exercise: ExerciseLibraryExercise)
}
