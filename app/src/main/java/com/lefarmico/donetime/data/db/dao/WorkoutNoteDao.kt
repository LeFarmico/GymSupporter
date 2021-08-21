package com.lefarmico.donetime.data.db.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.lefarmico.donetime.data.db.entities.WorkoutNote
import com.lefarmico.donetime.data.entities.workout.WorkoutData
import io.reactivex.rxjava3.core.Observable

@Dao
interface WorkoutNoteDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertWorkoutNote(workoutNote: WorkoutNote)

    @Query("SELECT * FROM workout_history")
    fun getWorkoutNotes(): Observable<List<WorkoutNote>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertWorkoutData(workoutData: WorkoutData)
}
