package com.lefarmico.donetime.data.db

import androidx.room.Database
import androidx.room.RoomDatabase
import com.lefarmico.donetime.data.db.dao.ExerciseLibraryDao
import com.lefarmico.donetime.data.db.entities.ExerciseLibraryCategory
import com.lefarmico.donetime.data.db.entities.ExerciseLibraryExercise
import com.lefarmico.donetime.data.db.entities.ExerciseLibrarySubCategory

@Database(
    entities = [
        ExerciseLibraryCategory::class,
        ExerciseLibrarySubCategory::class,
        ExerciseLibraryExercise::class
    ],
    version = 1
)
abstract class AppDataBase : RoomDatabase() {
    abstract fun itemDao(): ExerciseLibraryDao
}
