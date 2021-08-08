package com.lefarmico.donetime.data.db

import androidx.room.Database
import androidx.room.RoomDatabase
import com.lefarmico.donetime.data.db.dao.ExerciseLibraryDao
import com.lefarmico.donetime.data.db.entities.LibraryCategory
import com.lefarmico.donetime.data.db.entities.LibraryExercise
import com.lefarmico.donetime.data.db.entities.LibrarySubCategory

@Database(
    entities = [
        LibraryCategory::class,
        LibrarySubCategory::class,
        LibraryExercise::class
    ],
    version = 1
)
abstract class AppDataBase : RoomDatabase() {
    abstract fun itemDao(): ExerciseLibraryDao
}
