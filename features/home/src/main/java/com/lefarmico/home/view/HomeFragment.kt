package com.lefarmico.home.view

import android.os.Bundle
import android.view.Menu
import android.view.MenuInflater
import android.view.MenuItem
import android.widget.Toast
import com.lefarmico.core.adapter.WorkoutRecordsAdapter
import com.lefarmico.core.base.BaseFragment
import com.lefarmico.domain.utils.DataState
import com.lefarmico.home.R
import com.lefarmico.home.databinding.FragmentHomeBinding
import com.lefarmico.home.intent.HomeIntent
import com.lefarmico.home.viewModel.HomeViewModel

class HomeFragment : BaseFragment<FragmentHomeBinding, HomeViewModel>(
    FragmentHomeBinding::inflate,
    HomeViewModel::class.java
) {

    private val noteAdapter = WorkoutRecordsAdapter()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setHasOptionsMenu(true)
    }

    override fun setUpViews() {
        pushIntent(HomeIntent.GetWorkoutRecords)

        noteAdapter.editButtonCallback = {
            pushIntent(HomeIntent.EditWorkout(it.id))
        }
        binding.workoutNotes.adapter = noteAdapter

        binding.newWorkoutButton.setOnClickListener {
            pushIntent(HomeIntent.StartWorkoutScreen)
        }
    }

    override fun observeData() {
        viewModel.workoutRecordsLiveData.observe(viewLifecycleOwner) { dataState ->
            when (dataState) {
                DataState.Empty -> {
                    binding.state.showEmptyState()
                }
                is DataState.Error -> {
                    binding.state.showErrorState()
                }
                DataState.Loading -> {
                    binding.state.showLoadingState()
                }
                is DataState.Success -> {
                    binding.state.showSuccessState()
                    noteAdapter.items = dataState.data.toMutableList()
                }
            }
        }
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater.inflate(R.menu.app_bar_menu, menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            R.id.edit -> {
                Toast.makeText(requireContext(), "Edit", Toast.LENGTH_SHORT).show()
                true
            }
            else -> { false }
        }
    }

    private fun pushIntent(eventType: HomeIntent) {
        viewModel.onTriggerEvent(eventType)
    }
}
