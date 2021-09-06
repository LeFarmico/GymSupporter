package com.lefarmico.presentation.views.fragments

import android.view.View
import com.lefarmico.domain.utils.DataState
import com.lefarmico.presentation.R
import com.lefarmico.presentation.adapters.WorkoutNoteAdapter
import com.lefarmico.presentation.databinding.FragmentHomeBinding
import com.lefarmico.presentation.intents.HomeIntent
import com.lefarmico.presentation.viewModels.HomeViewModel
import com.lefarmico.presentation.views.base.BaseFragment

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
        viewModel.onTriggerEvent(HomeIntent.GetWorkoutRecords)
    }

    override fun observeData() {
        
        viewModel.noteWorkoutLiveData.observe(viewLifecycleOwner) { dataState ->
            when (dataState) {
                DataState.Empty -> {
                    binding.emptyState.root.visibility = View.VISIBLE
                    binding.errorState.root.visibility = View.GONE
                    binding.loadingState.root.visibility = View.GONE
                }
                is DataState.Error -> {
                    binding.errorState.root.visibility = View.VISIBLE
                    binding.emptyState.root.visibility = View.GONE
                    binding.loadingState.root.visibility = View.GONE
                }
                DataState.Loading -> {
                    binding.loadingState.root.visibility = View.VISIBLE
                    binding.emptyState.root.visibility = View.GONE
                    binding.errorState.root.visibility = View.GONE
                }
                is DataState.Success -> {
                    noteAdapter.items = dataState.data.toMutableList()
                    binding.emptyState.root.visibility = View.GONE
                    binding.errorState.root.visibility = View.GONE
                    binding.loadingState.root.visibility = View.GONE
                }
            }
        }
    }
}
