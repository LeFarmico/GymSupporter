package com.lefarmico.workout.view

import android.os.Bundle
import android.os.Parcelable
import android.view.ActionMode
import android.view.Menu
import android.view.MenuInflater
import android.view.MenuItem
import com.lefarmico.core.BuildConfig
import com.lefarmico.core.adapter.CurrentExerciseAdapter
import com.lefarmico.core.base.BaseFragment
import com.lefarmico.core.dialog.setParameter.SetSettingDialogCallback
import com.lefarmico.core.entity.CurrentWorkoutViewData.ExerciseWithSets
import com.lefarmico.core.selector.SelectItemsHandler
import com.lefarmico.core.toolbar.RemoveActionBarCallback
import com.lefarmico.core.toolbar.RemoveActionBarEvents.*
import com.lefarmico.domain.utils.DataState
import com.lefarmico.navigation.params.WorkoutScreenParams
import com.lefarmico.navigation.params.WorkoutScreenParams.*
import com.lefarmico.workout.R
import com.lefarmico.workout.databinding.FragmentWorkoutScreenBinding
import com.lefarmico.workout.intent.WorkoutScreenIntent
import com.lefarmico.workout.intent.WorkoutScreenIntent.*
import com.lefarmico.workout.viewModel.WorkoutScreenViewModel

class WorkoutScreenFragment :
    BaseFragment<FragmentWorkoutScreenBinding, WorkoutScreenViewModel>(
        FragmentWorkoutScreenBinding::inflate,
        WorkoutScreenViewModel::class.java
    ),
    SetSettingDialogCallback {

    private val adapter = CurrentExerciseAdapter()

    private var actionMode: ActionMode? = null
    private var selectHandler: SelectItemsHandler<ExerciseWithSets>? = null
    private var actionModeCallback: RemoveActionBarCallback? = null

    private val params: WorkoutScreenParams by lazy {
        arguments?.getParcelable<WorkoutScreenParams>(KEY_PARAMS)
            ?: throw (IllegalArgumentException())
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setHasOptionsMenu(true)

        when (params) {
            is NewExercise -> {
                val data = params as NewExercise
                startEvent(AddExercise(data.id))
            }
            else -> {}
        }
    }

    override fun setUpViews() {
        startEvent(GetAll)

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
        selectHandler = object : SelectItemsHandler<ExerciseWithSets>(adapter) {
            override fun selectedItemAction(item: ExerciseWithSets) {
                startEvent(DeleteExercise(item.exercise.id))
            }
        }
        binding.apply {
            listRecycler.adapter = adapter

            addExButton.setOnClickListener {
                startEvent(GoToCategoryScreen)
            }
            finishButton.setOnClickListener {
                startEvent(FinishWorkout)
            }
        }

        adapter.apply {
            plusButtonCallback = {
                launchSetParameterDialog(it)
            }
            minusButtonCallback = {
                startEvent(DeleteLastSet(it))
            }
            infoButtonCallback = {
                startEvent(GoToExerciseInfo(it))
            }
        }
    }

    override fun observeData() {
        viewModel.notificationLiveData.observe(viewLifecycleOwner) { string ->
            startEvent(ShowToast(string))
        }
        viewModel.actionBarLiveData.observe(viewLifecycleOwner) { event ->
            when (event) {
                SelectAll -> adapter.toggleSelectAll()
                DeleteItems -> {
                    selectHandler?.onEachSelectedItemsAction()
                    actionMode?.finish()
                }
                Launch -> {
                    adapter.turnOnEditState()
                    actionMode = requireActivity().startActionMode(actionModeCallback)
                }
                Close -> {
                    adapter.turnOffEditState()
                    actionMode?.finish()
                }
            }
        }
        viewModel.exerciseLiveData.observe(viewLifecycleOwner) { dataState ->
            when (dataState) {
                DataState.Empty -> {
                    binding.state.showEmptyState()
                    adapter.items = listOf()
                }
                is DataState.Error -> {
                    binding.state.showErrorState()
                    adapter.items = listOf()
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

        viewModel.setParametersDialogLiveData.observe(viewLifecycleOwner) { dataState ->
            when (dataState) {
                is DataState.Success -> {
                    startEvent(
                        ShowSetParametersDialog(
                            parentFragmentManager,
                            dataState.data.toInt(),
                            this,
                            TAG_DIALOG
                        )
                    )
                }
                else -> {}
            }
        }
    }

    override fun addSet(exerciseId: Int, reps: Int, weight: Float) {
        startEvent(AddSetToExercise(exerciseId, reps, weight))
    }

    private fun launchSetParameterDialog(exerciseId: Int) {
        startEvent(
            ShowSetParametersDialog(
                childFragmentManager,
                exerciseId,
                this,
                TAG_DIALOG
            )
        )
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
            else -> false
        }
    }

    private fun startEvent(event: WorkoutScreenIntent) {
        viewModel.onTriggerEvent(event)
    }
    companion object {
        private const val KEY_PARAMS = "home_key"
        private const val TAG_DIALOG = "Set Setting"

        fun createBundle(data: Parcelable?): Bundle {
            return Bundle().apply {
                when (data) {
                    is NewExercise -> putParcelable(KEY_PARAMS, data)
                    is Empty -> putParcelable(KEY_PARAMS, data)
                    else -> {
                        if (BuildConfig.DEBUG) {
                            throw (
                                IllegalArgumentException(
                                    "data should be WorkoutScreenParams type." +
                                        "But it's ${data!!.javaClass.canonicalName} type"
                                )
                                )
                        }
                    }
                }
            }
        }
    }
}
