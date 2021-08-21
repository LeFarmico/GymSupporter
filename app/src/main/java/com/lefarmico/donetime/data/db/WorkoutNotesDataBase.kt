package com.lefarmico.donetime.data.db

import androidx.room.Database
import androidx.room.RoomDatabase
import com.lefarmico.donetime.data.db.dao.WorkoutNoteDao
import com.lefarmico.donetime.data.db.entities.WorkoutNote
import com.lefarmico.donetime.data.entities.workout.WorkoutData

@Database(
    entities = [
        WorkoutNote::class,
        WorkoutData::class
    ],
    version = 1
)
abstract class WorkoutNotesDataBase : RoomDatabase() {
    abstract fun itemDao(): WorkoutNoteDao
}
