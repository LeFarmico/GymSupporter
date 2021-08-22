package com.lefarmico.donetime.data.entities.note

data class NoteExercise(
    val exerciseName: String,
    val noteSetList: List<NoteSet>
) {
    val setNumber: Int = 0
}
