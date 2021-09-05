package com.lefarmico.donetime.views.fragments

import android.annotation.SuppressLint
import android.os.Bundle
import android.widget.Toast
import androidx.core.util.Preconditions
import com.lefarmico.domain.utils.DataState
import com.lefarmico.donetime.R
import com.lefarmico.donetime.adapters.CurrentExercisesAdapter
import com.lefarmico.donetime.customView.setParameters.SetParametersDialog
import com.lefarmico.donetime.customView.setParameters.SetSettingDialogCallback
import com.lefarmico.donetime.databinding.FragmentWorkoutScreenBinding
import com.lefarmico.donetime.intents.WorkoutScreenIntent
import com.lefarmico.donetime.viewModels.WorkoutScreenViewModel
import com.lefarmico.donetime.views.base.BaseFragment
import com.lefarmico.donetime.views.fragments.listMenu.workout.WorkoutCategoryFragment

class WorkoutScreenFragment :
    BaseFragment<FragmentWorkoutScreenBinding, WorkoutScreenViewModel>(
        FragmentWorkoutScreenBinding::inflate,
        WorkoutScreenViewModel::class.java
    ),
    SetSettingDialogCallback {

    val adapter = CurrentExercisesAdapter().apply {
        plusButtonCallBack = {
            initSetParameterDialog(it)
        }
        minusButtonCallback = {
            viewModel.onTriggerEvent(
                WorkoutScreenIntent.DeleteSet(it)
            )
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        parentFragmentManager
            .setFragmentResultListener(
                REQUEST_KEY,
                this,
                { requestKey, result ->
                    onFragmentResult(requestKey, result)
                }
            )
    }

    override fun setUpViews() {
        binding.listRecycler.adapter = adapter
        viewModel.exerciseLiveData.observe(viewLifecycleOwner) { dataState ->
            when (dataState) {
                DataState.Empty -> {}
                is DataState.Error -> {}
                DataState.Loading -> {}
                is DataState.Success -> {
                    adapter.items = dataState.data
                }
            }
        }
        binding.addExButton.setOnClickListener {
            val bundle = Bundle()
            bundle.putBoolean(ADD_EXERCISE_BRANCH, true)
            parentFragmentManager.beginTransaction()
                .replace(R.id.fragment, WorkoutCategoryFragment::class.java, bundle)
                .addToBackStack(BACKSTACK_BRANCH)
                .commit()
        }
        binding.finishButton.setOnClickListener {
            viewModel.onTriggerEvent(
                WorkoutScreenIntent.SaveAll
            )
            Toast.makeText(requireContext(), "Your workout saved!", Toast.LENGTH_SHORT).show()
            parentFragmentManager.beginTransaction()
                .replace(R.id.fragment, HomeFragment::class.java, null)
                .commit()
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
    
    companion object {
        const val REQUEST_KEY = "exercise_result"
        const val KEY_NUMBER = "key_1"
        const val BACKSTACK_BRANCH = "EXERCISE"
        const val ADD_EXERCISE_BRANCH = "add_exercise_branch"
    }
}
