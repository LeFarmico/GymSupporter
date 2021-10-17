package com.lefarmico.data.db

import androidx.room.Database
import androidx.room.RoomDatabase
import com.lefarmico.data.db.dao.CurrentWorkoutDao
import com.lefarmico.data.db.entity.CurrentWorkoutData

@Database(
    entities = [
        CurrentWorkoutData.Exercise::class,
        CurrentWorkoutData.Set::class,
        CurrentWorkoutData.ExerciseSetCrossRef::class
    ],
    version = 1,
    exportSchema = false
)
abstract class CurrentWorkoutDataBase : RoomDatabase() {
    abstract fun currentWorkoutDao(): CurrentWorkoutDao
}
