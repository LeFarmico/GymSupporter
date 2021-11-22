package com.lefarmico.data.db

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.lefarmico.data.converter.DateConverter
import com.lefarmico.data.db.dao.WorkoutRecordsDao
import com.lefarmico.data.db.entity.WorkoutRecordsData

@Database(
    entities = [
        WorkoutRecordsData.Workout::class,
        WorkoutRecordsData.Exercise::class,
        WorkoutRecordsData.Set::class,
    ],
    version = 1,
    exportSchema = false
)
@TypeConverters(DateConverter::class)
abstract class WorkoutRecordsDataBase : RoomDatabase() {
    abstract fun itemDao(): WorkoutRecordsDao
}
