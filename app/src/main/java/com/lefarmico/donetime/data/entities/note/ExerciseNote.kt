package com.lefarmico.donetime.data.entities.note

data class ExerciseNote(
    val exerciseName: String,
    val setNoteList: List<SetNote>
) {
    val setNumber: Int = 0
}
