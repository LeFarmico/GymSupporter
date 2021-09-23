package com.lefarmico.workout.view

import android.os.Bundle
import android.os.Parcelable
import android.view.Menu
import android.view.MenuInflater
import android.view.MenuItem
import android.widget.Toast
import com.lefarmico.core.BuildConfig
import com.lefarmico.core.adapter.CurrentExercisesAdapter
import com.lefarmico.core.base.BaseFragment
import com.lefarmico.core.dialog.setParameter.SetParametersDialog
import com.lefarmico.core.dialog.setParameter.SetSettingDialogCallback
import com.lefarmico.domain.utils.DataState
import com.lefarmico.navigation.params.WorkoutScreenParams
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

    // TODO delegate?
    private val params: WorkoutScreenParams by lazy {
        arguments?.getParcelable<WorkoutScreenParams>(KEY_PARAMS) ?: throw (IllegalArgumentException())
    }

    private val adapter = CurrentExercisesAdapter()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setHasOptionsMenu(true)

        when (params) {
            is WorkoutScreenParams.NewExercise -> {
                val data = params as WorkoutScreenParams.NewExercise
                viewModel.onTriggerEvent(WorkoutScreenIntent.AddExercise(data.id))
            }
            else -> {}
        }
    }

    override fun setUpViews() {
        viewModel.onTriggerEvent(WorkoutScreenIntent.GetAll)
        binding.apply {
            listRecycler.adapter = adapter
            addExButton.setOnClickListener {
                viewModel.onTriggerEvent(
                    WorkoutScreenIntent.GoToCategoryScreen
                )
            }
            finishButton.setOnClickListener {
                viewModel.onTriggerEvent(WorkoutScreenIntent.SaveAll)
                viewModel.onTriggerEvent(WorkoutScreenIntent.FinishWorkout)
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
        private const val KEY_PARAMS = "home_key"

        fun createBundle(data: Parcelable?): Bundle {
            return Bundle().apply {
                when (data) {
                    is WorkoutScreenParams.NewExercise -> putParcelable(KEY_PARAMS, data)
                    is WorkoutScreenParams.Empty -> putParcelable(KEY_PARAMS, data)
                    else -> {
                        if (BuildConfig.DEBUG) {
                            throw (
                                IllegalArgumentException(
                                    "data should be WorkoutScreenParams type." +
                                        "But it's ${data!!.javaClass.simpleName} type"
                                )
                                )
                        }
                    }
                }
            }
        }
    }
}
