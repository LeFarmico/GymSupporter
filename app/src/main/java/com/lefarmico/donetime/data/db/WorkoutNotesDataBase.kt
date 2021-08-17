package com.lefarmico.donetime.data.db

import androidx.room.Database
import androidx.room.RoomDatabase
import com.lefarmico.donetime.data.db.dao.WorkoutNoteDao
import com.lefarmico.donetime.data.db.entities.WorkoutNote

@Database(
    entities = [
        WorkoutNote::class
    ],
    version = 1
)
abstract class WorkoutNotesDataBase : RoomDatabase() {
    abstract fun itemDao(): WorkoutNoteDao
}
