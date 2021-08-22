package com.lefarmico.donetime.data.db

import androidx.room.Database
import androidx.room.RoomDatabase
import com.lefarmico.donetime.data.db.dao.ExerciseLibraryDao
import com.lefarmico.donetime.data.entities.library.LibraryCategory
import com.lefarmico.donetime.data.entities.library.LibraryExercise
import com.lefarmico.donetime.data.entities.library.LibrarySubCategory

@Database(
    entities = [
        LibraryCategory::class,
        LibrarySubCategory::class,
        LibraryExercise::class
    ],
    version = 1
)
abstract class LibraryDataBase : RoomDatabase() {
    abstract fun itemDao(): ExerciseLibraryDao
}
