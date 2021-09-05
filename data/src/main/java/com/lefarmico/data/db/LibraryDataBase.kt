package com.lefarmico.data.db

import androidx.room.Database
import androidx.room.RoomDatabase
import com.lefarmico.data.db.dao.LibraryDao
import com.lefarmico.data.db.entity.LibraryData

@Database(
    entities = [
        LibraryData.Category::class,
        LibraryData.SubCategory::class,
        LibraryData.Exercise::class
    ],
    version = 1,
    exportSchema = false
)
abstract class LibraryDataBase : RoomDatabase() {
    abstract fun libraryDao(): LibraryDao
}
