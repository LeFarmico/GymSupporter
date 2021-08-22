package com.lefarmico.donetime.data.db

import androidx.room.Database
import androidx.room.RoomDatabase
import com.lefarmico.donetime.data.db.dao.WorkoutNoteDao
import com.lefarmico.donetime.data.entities.note.NoteWorkout

@Database(
    entities = [
        NoteWorkout::class
    ],
    version = 1
)
abstract class WorkoutNotesDataBase : RoomDatabase() {
    abstract fun itemDao(): WorkoutNoteDao
}
