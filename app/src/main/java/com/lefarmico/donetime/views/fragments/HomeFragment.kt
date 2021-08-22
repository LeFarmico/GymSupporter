package com.lefarmico.donetime.views.fragments

import com.lefarmico.donetime.R
import com.lefarmico.donetime.adapters.WorkoutNoteAdapter
import com.lefarmico.donetime.data.entities.note.NoteExercise
import com.lefarmico.donetime.data.entities.note.NoteSet
import com.lefarmico.donetime.data.entities.note.NoteWorkout
import com.lefarmico.donetime.databinding.FragmentHomeBinding
import com.lefarmico.donetime.viewModels.HomeViewModel
import com.lefarmico.donetime.views.base.BaseFragment

class HomeFragment : BaseFragment<FragmentHomeBinding, HomeViewModel>(
    FragmentHomeBinding::inflate,
    HomeViewModel::class.java
) {

    override fun setUpViews() {
        binding.newWorkoutButton.setOnClickListener {
            parentFragmentManager.beginTransaction()
                .replace(R.id.fragment, WorkoutScreenFragment::class.java, null)
                .commit()
        }

        val notes = mutableListOf<NoteSet>()
        notes.add(NoteSet(1, 10f, 10))
        notes.add(NoteSet(2, 10f, 20))
        notes.add(NoteSet(3, 10f, 30))
        notes.add(NoteSet(4, 10f, 15))

        val exerciseNote = NoteExercise(
            exerciseName = "Bench press",
            noteSetList = notes
        )
        val exercises = mutableListOf<NoteExercise>()
        exercises.add(exerciseNote)
        exercises.add(exerciseNote)

        val workoutNote = NoteWorkout(
            date = "23.12.2021",
            noteExerciseList = mutableListOf(exerciseNote, exerciseNote)
        )
        val adapter = WorkoutNoteAdapter()
        adapter.items = mutableListOf(workoutNote, workoutNote)
        binding.workoutNotes.adapter = adapter
    }
}
