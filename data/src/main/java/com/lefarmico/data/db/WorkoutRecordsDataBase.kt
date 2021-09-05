package com.lefarmico.data.db

import androidx.room.Database
import androidx.room.RoomDatabase
import com.lefarmico.data.db.dao.WorkoutRecordsDao
import com.lefarmico.data.db.entity.WorkoutRecordsData

@Database(
    entities = [
        WorkoutRecordsData.Workout::class
    ],
    version = 1,
    exportSchema = false
)
abstract class WorkoutRecordsDataBase : RoomDatabase() {
    abstract fun itemDao(): WorkoutRecordsDao
}
