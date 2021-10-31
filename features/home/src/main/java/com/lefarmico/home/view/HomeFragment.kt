package com.lefarmico.home.view

import android.os.Bundle
import android.view.ActionMode
import android.view.Menu
import android.view.MenuInflater
import android.view.MenuItem
import com.lefarmico.core.adapter.WorkoutRecordsAdapter
import com.lefarmico.core.base.BaseFragment
import com.lefarmico.core.entity.WorkoutRecordsViewData.*
import com.lefarmico.core.selector.SelectItemsHandler
import com.lefarmico.core.toolbar.RemoveActionBarCallback
import com.lefarmico.core.toolbar.RemoveActionBarEvents.*
import com.lefarmico.domain.utils.DataState
import com.lefarmico.home.R
import com.lefarmico.home.databinding.FragmentHomeBinding
import com.lefarmico.home.intent.HomeIntent
import com.lefarmico.home.intent.HomeIntent.*
import com.lefarmico.home.viewModel.HomeViewModel

class HomeFragment : BaseFragment<FragmentHomeBinding, HomeViewModel>(
    FragmentHomeBinding::inflate,
    HomeViewModel::class.java
) {

    private val noteAdapter = WorkoutRecordsAdapter()

    private var actionMode: ActionMode? = null
    private var selectHandler: SelectItemsHandler<WorkoutWithExercisesAndSets>? = null
    private var actionModeCallback: RemoveActionBarCallback? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setHasOptionsMenu(true)
    }

    override fun setUpViews() {
        startEvent(GetWorkoutRecords)

        binding.workoutNotes.adapter = noteAdapter.apply {
            onEditButtonAction = {
                startEvent(NavigateToDetailsWorkout(it.id))
            }
        }
        binding.newWorkoutButton.setOnClickListener {
            startEvent(NavigateToWorkout)
        }
        actionModeCallback = object : RemoveActionBarCallback() {
            override fun selectAllButtonHandler() {
                startEvent(ActionBarEvent(SelectAll))
            }

            override fun removeButtonHandler() {
                startEvent(ActionBarEvent(DeleteItems))
            }

            override fun onDestroyHandler() {
                startEvent(ActionBarEvent(Close))
            }
        }
        selectHandler = object : SelectItemsHandler<WorkoutWithExercisesAndSets>(noteAdapter) {
            override fun selectedItemAction(item: WorkoutWithExercisesAndSets) {
                startEvent(RemoveWorkout(item.workout.id))
            }
        }
    }

    override fun observeData() {
        viewModel.actionBarLiveData.observe(viewLifecycleOwner) { event ->
            when (event) {
                SelectAll -> noteAdapter.toggleSelectAll()
                DeleteItems -> {
                    selectHandler?.onEachSelectedItemsAction()
                    actionMode?.finish()
                }
                Launch -> {
                    noteAdapter.turnOnEditState()
                    actionMode = requireActivity().startActionMode(actionModeCallback)
                }
                Close -> {
                    noteAdapter.turnOffEditState()
                    actionMode?.finish()
                }
            }
        }
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
                    noteAdapter.items = dataState.data
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
                startEvent(ActionBarEvent(Launch))
                true
            }
            else -> { false }
        }
    }

    private fun startEvent(eventType: HomeIntent) {
        viewModel.onTriggerEvent(eventType)
    }
}
