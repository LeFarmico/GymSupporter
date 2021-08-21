package com.lefarmico.donetime.views.fragments

import com.lefarmico.donetime.R
import com.lefarmico.donetime.adapters.WorkoutNoteAdapter
import com.lefarmico.donetime.data.entities.note.ExerciseNote
import com.lefarmico.donetime.data.entities.note.SetNote
import com.lefarmico.donetime.data.entities.note.WorkoutNote
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

        val notes = mutableListOf<SetNote>()
        notes.add(SetNote(1, 10f, 10))
        notes.add(SetNote(2, 10f, 20))
        notes.add(SetNote(3, 10f, 30))
        notes.add(SetNote(4, 10f, 15))

        val exerciseNote = ExerciseNote(
            exerciseName = "Bench press",
            setNoteList = notes
        )
        val exercises = mutableListOf<ExerciseNote>()
        exercises.add(exerciseNote)
        exercises.add(exerciseNote)

        val workoutNote = WorkoutNote(
            date = "23.12.2021",
            exerciseNoteList = mutableListOf(exerciseNote, exerciseNote)
        )
        val adapter = WorkoutNoteAdapter()
        adapter.items = mutableListOf(workoutNote, workoutNote)
        binding.workoutNotes.adapter = adapter
    }
}
