package com.lefarmico.presentation.views.fragments

import android.os.Bundle
import android.view.View
import com.lefarmico.domain.utils.DataState
import com.lefarmico.presentation.R
import com.lefarmico.presentation.adapters.WorkoutNoteAdapter
import com.lefarmico.presentation.databinding.FragmentHomeBinding
import com.lefarmico.presentation.di.provider.PresentationComponentProvider
import com.lefarmico.presentation.intents.HomeIntent
import com.lefarmico.presentation.viewModels.HomeViewModel
import com.lefarmico.presentation.views.base.BaseFragment

class HomeFragment : BaseFragment<FragmentHomeBinding, HomeViewModel>(
    FragmentHomeBinding::inflate,
    HomeViewModel::class.java
) {

    private val noteAdapter = WorkoutNoteAdapter()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        (activity?.application as PresentationComponentProvider)
            .getPresentationComponent()
            .inject(viewModel)
    }
    override fun setUpViews() {
        viewModel.onTriggerEvent(HomeIntent.GetWorkoutRecords)
        binding.workoutNotes.adapter = noteAdapter

        binding.newWorkoutButton.setOnClickListener {
            parentFragmentManager.beginTransaction()
                .replace(R.id.fragment, WorkoutScreenFragment::class.java, null)
                .commit()
        }
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
                    binding.emptyState.root.visibility = View.GONE
                    binding.errorState.root.visibility = View.GONE
                    binding.loadingState.root.visibility = View.GONE

                    noteAdapter.items = dataState.data.toMutableList()
                }
            }
        }
    }
}
