package com.lefarmico.data.db.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Transaction
import androidx.room.Update
import com.lefarmico.data.db.entity.CurrentWorkoutData
import io.reactivex.rxjava3.core.Observable
import io.reactivex.rxjava3.core.Single

@Dao
interface CurrentWorkoutDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertExercise(exercise: CurrentWorkoutData.Exercise): Long

    @Delete
    fun deleteExercise(exercise: CurrentWorkoutData.Exercise): Int

    @Query("SELECT * FROM current_exercise WHERE exercise_id = :exerciseId")
    fun getExercise(exerciseId: Int): Observable<CurrentWorkoutData.Exercise>

    @Transaction
    @Query("SELECT * FROM current_exercise WHERE exercise_id = :exerciseId")
    fun getExerciseWithSets(exerciseId: Int): Single<CurrentWorkoutData.ExerciseWithSets>

    @Transaction
    @Query("SELECT * FROM current_exercise")
    fun getExercisesWithSets(): Observable<List<CurrentWorkoutData.ExerciseWithSets>>

    @Update
    fun updateExercise(exercise: CurrentWorkoutData.Exercise): Int

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertSet(set: CurrentWorkoutData.Set): Long

    @Delete
    fun deleteSet(set: CurrentWorkoutData.Set): Int

    @Delete
    fun deleteSets(setList: List<CurrentWorkoutData.Set>): Int

    @Query("SELECT * FROM current_set WHERE set_id = :setId")
    fun getSet(setId: Int): Observable<CurrentWorkoutData.Set>

    @Query("SELECT * FROM current_set WHERE exercise_id = :exerciseId")
    fun getSets(exerciseId: Int): Observable<List<CurrentWorkoutData.Set>>

    @Update
    fun updateSet(set: CurrentWorkoutData.Set): Int

    @Query("DELETE FROM current_exercise")
    fun clearExerciseData()

    @Query("DELETE FROM current_set")
    fun clearSetData()
}
