package com.lefarmico.data.db.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.lefarmico.data.db.entity.WorkoutRecordsData
import io.reactivex.rxjava3.core.Observable

@Dao
interface WorkoutRecordsDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertWorkoutNote(noteWorkout: WorkoutRecordsData.Workout): Long

    @Query("SELECT * FROM workout_records")
    fun getWorkoutNotes(): Observable<List<WorkoutRecordsData.Workout>>
}
