package com.lefarmico.donetime.views.fragments

import com.lefarmico.donetime.R
import com.lefarmico.donetime.adapters.WorkoutNoteAdapter
import com.lefarmico.donetime.databinding.FragmentHomeBinding
import com.lefarmico.donetime.viewModels.HomeViewModel
import com.lefarmico.donetime.views.base.BaseFragment

class HomeFragment : BaseFragment<FragmentHomeBinding, HomeViewModel>(
    FragmentHomeBinding::inflate,
    HomeViewModel::class.java
) {

    private val noteAdapter = WorkoutNoteAdapter()

    override fun setUpViews() {
        binding.newWorkoutButton.setOnClickListener {
            parentFragmentManager.beginTransaction()
                .replace(R.id.fragment, WorkoutScreenFragment::class.java, null)
                .commit()
        }

        binding.workoutNotes.adapter = noteAdapter
    }

    override fun observeData() {

        viewModel.noteWorkoutLiveData.observe(viewLifecycleOwner) {
            noteAdapter.items = it.toMutableList()
        }
    }
}
