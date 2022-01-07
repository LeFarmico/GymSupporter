package com.lefarmico.workout

import android.os.Bundle
import android.os.Parcelable
import android.view.*
import com.lefarmico.core.BuildConfig
import com.lefarmico.core.adapter.CurrentExerciseAdapter
import com.lefarmico.core.base.BaseFragment
import com.lefarmico.core.entity.CurrentWorkoutViewData.ExerciseWithSets
import com.lefarmico.core.selector.SelectItemsHandler
import com.lefarmico.core.toolbar.EditActionBarEvents.*
import com.lefarmico.core.toolbar.RemoveActionBarCallback
import com.lefarmico.navigation.params.WorkoutScreenParams
import com.lefarmico.navigation.params.WorkoutScreenParams.*
import com.lefarmico.workout.WorkoutIntent.*
import com.lefarmico.workout.databinding.FragmentWorkoutScreenBinding

class WorkoutFragment :
    BaseFragment<
        WorkoutIntent, WorkoutAction, WorkoutState, WorkoutEvent,
        FragmentWorkoutScreenBinding, WorkoutViewModel>(
        FragmentWorkoutScreenBinding::inflate,
        WorkoutViewModel::class.java
    ) {

    private val adapter = CurrentExerciseAdapter().apply {
        plusButtonCallback = { dispatchIntent(StartSetParameterDialog(it)) }
        minusButtonCallback = { dispatchIntent(DeleteLastSet(it)) }
        infoButtonCallback = { dispatchIntent(GoToExerciseInfo(it)) }
    }

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
                dispatchIntent(AddExercise(data.id))
            }
            else -> {}
        }
    }

    override fun setUpViews() {
        dispatchIntent(GetExercises)
        dispatchIntent(GetSelectedDate)
        dispatchIntent(GetTitle)

        actionModeCallback = object : RemoveActionBarCallback() {
            override fun selectAllButtonHandler() {
                dispatchIntent(SelectAllWorkouts)
            }

            override fun removeButtonHandler() {
                dispatchIntent(DeleteSelectedWorkouts)
            }

            override fun onDestroyHandler() {
                dispatchIntent(HideEditState)
            }
        }
        selectHandler = object : SelectItemsHandler<ExerciseWithSets>(adapter) {
            override fun selectedItemAction(item: ExerciseWithSets) {
                dispatchIntent(DeleteExercise(item.exercise.id))
            }
        }
        binding.apply {
            listRecycler.adapter = adapter

            addExButton.setOnClickListener { dispatchIntent(GoToCategoryScreen) }
            finishButton.setOnClickListener { dispatchIntent(FinishWorkout) }
            workoutTitle.setOnClickListener { dispatchIntent(StartWorkoutTitleDialog) }
            workoutDate.setOnClickListener { dispatchIntent(StartCalendarPickerDialog) }
        }
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater.inflate(R.menu.app_bar_menu, menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            R.id.edit -> {
                dispatchIntent(ShowEditState)
                true
            }
            else -> false
        }
    }

    private fun showLoading() = binding.state.showLoadingState()

    private fun showExercises(items: List<ExerciseWithSets>) {
        adapter.items = items
        if (items.isEmpty()) {
            binding.state.showEmptyState()
            return
        }
        binding.state.showSuccessState()
    }

    private fun showEditState() {
        adapter.turnOnEditState()
        actionMode = requireActivity().startActionMode(actionModeCallback)
    }

    private fun hideEditState() {
        adapter.turnOffEditState()
        actionMode?.finish()
    }

    private fun setWorkoutTitle(title: String) {
        binding.workoutTitle.text = title
    }

    private fun setWorkoutDate(date: String) {
        binding.workoutDate.text = date
    }

    private fun deleteSelectedExercises() {
        selectHandler?.onEachSelectedItemsAction()
        actionMode?.finish()
    }

    private fun selectAllExercises() {
        adapter.toggleSelectAll()
    }

    override fun receive(state: WorkoutState) {
        when (state) {
            is WorkoutState.DateResult -> setWorkoutDate(state.date)
            is WorkoutState.ExceptionResult -> throw (state.exception)
            is WorkoutState.ExerciseResult -> showExercises(state.exerciseList)
            WorkoutState.Loading -> showLoading()
            is WorkoutState.TitleResult -> setWorkoutTitle(state.title)
        }
    }

    override fun receive(event: WorkoutEvent) {
        when (event) {
            WorkoutEvent.ShowEditState -> showEditState()
            WorkoutEvent.SelectAllExercises -> selectAllExercises()
            WorkoutEvent.HideEditState -> hideEditState()
            WorkoutEvent.DeleteSelectedExercises -> deleteSelectedExercises()
            WorkoutEvent.DeselectAllExercises -> {}
            is WorkoutEvent.ExceptionEvent -> throw (event.exception)
            WorkoutEvent.Loading -> {}
            is WorkoutEvent.SetParamsDialog -> dispatchIntent(StartSetParameterDialog(event.exerciseId.toInt()))
        }
    }

    companion object {
        private const val KEY_PARAMS = "home_key"

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
