package com.lefarmico.workout.view

import android.annotation.SuppressLint
import android.os.Bundle
import android.view.Menu
import android.view.MenuInflater
import android.view.MenuItem
import android.widget.Toast
import androidx.core.util.Preconditions
import com.lefarmico.core.adapter.CurrentExercisesAdapter
import com.lefarmico.core.base.BaseFragment
import com.lefarmico.core.dialog.setParameter.SetParametersDialog
import com.lefarmico.core.dialog.setParameter.SetSettingDialogCallback
import com.lefarmico.domain.utils.DataState
import com.lefarmico.workout.R
import com.lefarmico.workout.databinding.FragmentWorkoutScreenBinding
import com.lefarmico.workout.intent.WorkoutScreenIntent
import com.lefarmico.workout.viewModel.WorkoutScreenViewModel

class WorkoutScreenFragment :
    BaseFragment<FragmentWorkoutScreenBinding, WorkoutScreenViewModel>(
        FragmentWorkoutScreenBinding::inflate,
        WorkoutScreenViewModel::class.java
    ),
    SetSettingDialogCallback {

    private val adapter = CurrentExercisesAdapter()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setHasOptionsMenu(true)

        parentFragmentManager.setFragmentResultListener(
            REQUEST_KEY,
            this,
            { requestKey, result ->
                onFragmentResult(requestKey, result)
            }
        )
    }

    override fun setUpViews() {
        viewModel.onTriggerEvent(WorkoutScreenIntent.GetAll)
        binding.apply {
            listRecycler.adapter = adapter
            addExButton.setOnClickListener {
                val bundle = Bundle().apply {
                    putBoolean(ADD_EXERCISE_BRANCH, true)
                }
                // TODO navigation module
//                parentFragmentManager.beginTransaction()
//                    .replace(R.id.fragment, WorkoutCategoryFragment::class.java, bundle)
//                    .addToBackStack(BACKSTACK_BRANCH)
//                    .commit()
            }
            finishButton.setOnClickListener {
                viewModel.onTriggerEvent(WorkoutScreenIntent.SaveAll)
                // TODO navigation module
//                parentFragmentManager.beginTransaction()
//                    .replace(R.id.fragment, com.lefarmico.home.views.HomeFragment::class.java, null)
//                    .commit()
            }
        }
        adapter.apply {
            plusButtonCallBack = {
                initSetParameterDialog(it)
            }
            minusButtonCallback = {
                viewModel.onTriggerEvent(
                    WorkoutScreenIntent.DeleteSet(it)
                )
            }
            onSelectExercise = {}
        }
    }

    override fun observeData() {
        viewModel.exerciseLiveData.observe(viewLifecycleOwner) { dataState ->
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
                    adapter.items = dataState.data
                    binding.state.showSuccessState()
                }
            }
        }
    }

    override fun addSet(exerciseId: Int, reps: Int, weight: Float) {
        viewModel.onTriggerEvent(
            WorkoutScreenIntent.AddSetToExercise(exerciseId, reps, weight)
        )
    }

    @SuppressLint("RestrictedApi")
    private fun onFragmentResult(requestKey: String, result: Bundle) {
        Preconditions.checkState(REQUEST_KEY == requestKey)
        val exercise = result.getInt(KEY_NUMBER)

        viewModel.onTriggerEvent(WorkoutScreenIntent.AddExercise(exercise))
    }

    private fun initSetParameterDialog(exerciseId: Int) {
        SetParametersDialog(exerciseId, this)
            .show(childFragmentManager, "Set Setting")
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater.inflate(R.menu.app_bar_menu, menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            R.id.edit -> {
                Toast.makeText(requireContext(), "Select exercise", Toast.LENGTH_SHORT).show()
                true
            }
            else -> { false }
        }
    }

    companion object {
        const val REQUEST_KEY = "exercise_result"
        const val KEY_NUMBER = "key_1"
        const val BACKSTACK_BRANCH = "EXERCISE"
        const val ADD_EXERCISE_BRANCH = "add_exercise_branch"
    }
}
