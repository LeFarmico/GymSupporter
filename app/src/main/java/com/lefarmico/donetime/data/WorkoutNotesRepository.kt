package com.lefarmico.donetime.data

import com.lefarmico.donetime.data.db.dao.WorkoutNoteDao
import com.lefarmico.donetime.data.entities.note.NoteWorkout
import io.reactivex.rxjava3.core.Observable

class WorkoutNotesRepository(private val dao: WorkoutNoteDao) {
    
    fun addWorkoutNote(noteWorkout: NoteWorkout) {
        dao.insertWorkoutNote(noteWorkout)
    }
    
    fun getWorkoutNotes(): Observable<List<NoteWorkout>> {
        return dao.getWorkoutNotes()
    }
}
