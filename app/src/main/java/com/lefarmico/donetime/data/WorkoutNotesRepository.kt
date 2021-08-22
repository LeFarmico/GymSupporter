package com.lefarmico.donetime.data

import com.lefarmico.donetime.data.db.dao.WorkoutNoteDao
import com.lefarmico.donetime.data.entities.note.WorkoutNote
import io.reactivex.rxjava3.core.Observable

class WorkoutNotesRepository(private val dao: WorkoutNoteDao) {
    
    fun addWorkoutNote(workoutNote: WorkoutNote) {
        dao.insertWorkoutNote(workoutNote)
    }
    
    fun getWorkoutNotes(): Observable<List<WorkoutNote>> {
        return dao.getWorkoutNotes()
    }
}
