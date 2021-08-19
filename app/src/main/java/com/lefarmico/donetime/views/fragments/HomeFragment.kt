package com.lefarmico.donetime.views.fragments

import com.lefarmico.donetime.R
import com.lefarmico.donetime.adapters.viewHolders.factories.NoteExerciseViewHolderFactory
import com.lefarmico.donetime.data.entities.notes.SetNote
import com.lefarmico.donetime.databinding.FragmentHomeBinding
import com.lefarmico.donetime.viewModels.HomeViewModel
import com.lefarmico.donetime.views.base.BaseFragment
import com.lefarmico.lerecycle.LeRecyclerAdapter
import com.lefarmico.lerecycle.extractValues

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
        binding.note.setsRecycler.adapter = LeRecyclerAdapter().apply {
            setItemTypes(extractValues<NoteExerciseViewHolderFactory>())
            items = notes.toMutableList()
        }
    }
}
