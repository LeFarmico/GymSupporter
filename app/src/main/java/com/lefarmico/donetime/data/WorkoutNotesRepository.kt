package com.lefarmico.donetime.data

import com.lefarmico.donetime.data.db.dao.WorkoutNoteDao
import com.lefarmico.donetime.data.entities.note.NoteWorkout
import io.reactivex.rxjava3.core.Observable

class WorkoutNotesRepository(private val dao: WorkoutNoteDao) {
    
    fun addNoteWorkout(noteWorkout: NoteWorkout) {
        dao.insertWorkoutNote(noteWorkout)
    }
    
    fun getNoteWorkouts(): Observable<List<NoteWorkout>> {
        return dao.getWorkoutNotes()
    }
}
