package com.lefarmico.workout

import android.os.Bundle
import android.os.Parcelable
import android.view.*
import androidx.appcompat.app.AppCompatActivity
import com.lefarmico.core.adapter.CurrentExerciseAdapter
import com.lefarmico.core.base.BaseFragment
import com.lefarmico.core.entity.CurrentWorkoutViewData.ExerciseWithSets
import com.lefarmico.core.selector.SelectItemsHandler
import com.lefarmico.core.toolbar.EditStateActionBarCallback
import com.lefarmico.navigation.params.WorkoutScreenParams
import com.lefarmico.navigation.params.WorkoutScreenParams.NewExercise
import com.lefarmico.navigation.params.WorkoutScreenParams.UpdateWorkout
import com.lefarmico.workout.WorkoutIntent.*
import com.lefarmico.workout.databinding.FragmentWorkoutScreenBinding

class WorkoutFragment :
    BaseFragment<
        WorkoutIntent, WorkoutState, WorkoutEvent,
        FragmentWorkoutScreenBinding, WorkoutViewModel>(
        FragmentWorkoutScreenBinding::inflate,
        WorkoutViewModel::class.java
    ) {

    private val adapter = CurrentExerciseAdapter().apply {
        plusButtonCallback = { dispatchIntent(Dialog.SetParamsDialog(it)) }
        minusButtonCallback = { dispatchIntent(ExSet.DeleteLastExSet(it)) }
        infoButtonCallback = { dispatchIntent(Navigate.ExerciseDetails(it)) }
    }

    private var actionMode: ActionMode? = null
    private var selectHandler: SelectItemsHandler<ExerciseWithSets>? = null
    private var actionModeCallback: EditStateActionBarCallback? = null

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
                dispatchIntent(Exercise.Add(data.id))
            }
            is UpdateWorkout -> {
                val data = params as UpdateWorkout
                dispatchIntent(Workout.Load(data.recordWorkoutId))
            }
            else -> {}
        }
    }

    override fun setUpViews() {
        dispatchIntent(Title.Get)
        dispatchIntent(Date.Get)
        dispatchIntent(Time.Get)
        dispatchIntent(Workout.New)
        dispatchIntent(SwitchState.Get)
        setUpToolbar()

        actionModeCallback = object : EditStateActionBarCallback() {
            override fun selectAllButtonHandler() { dispatchIntent(EditState.SelectAll) }
            override fun removeButtonHandler() { dispatchIntent(EditState.DeleteSelected) }
            override fun onDestroyHandler() { dispatchIntent(EditState.Hide) }
        }

        selectHandler = object : SelectItemsHandler<ExerciseWithSets>(adapter) {
            override fun selectedItemAction(item: ExerciseWithSets) {
                dispatchIntent(Exercise.Delete(item.exercise.id))
            }
        }

        binding.workoutTime.setOnClickListener {
            dispatchIntent(Dialog.TimeDialog)
        }
        binding.schedulerSwitch.setOnCheckedChangeListener { _, isChecked ->
            dispatchIntent(SwitchState.Set(isChecked))
        }
        binding.apply {
            listRecycler.adapter = adapter

            addExButton.setOnClickListener { dispatchIntent(Navigate.CategoryMenu) }
            finishButton.setOnClickListener { dispatchIntent(Workout.Finish) }
            workoutTitle.setOnClickListener { dispatchIntent(Dialog.TitleDialog) }
            workoutDate.setOnClickListener { dispatchIntent(Dialog.CalendarDialog) }
        }
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater.inflate(R.menu.app_bar_menu, menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            R.id.edit -> {
                dispatchIntent(EditState.Show)
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

    private fun workoutTitle(title: String) {
        binding.workoutTitle.text = title
    }

    private fun workoutDate(date: String) {
        binding.workoutDate.text = date
    }

    private fun workoutTime(time: String) {
        binding.workoutTime.text = time
    }

    private fun deleteSelectedExercises() {
        selectHandler?.onEachSelectedItemsAction()
        actionMode?.finish()
    }

    private fun selectAllExercises() {
        adapter.toggleSelectAll()
    }

    private fun switchState(state: Boolean) {
        when (state) {
            true -> {
                binding.schedulerSwitch.isChecked = state
                binding.schedulerText.alpha = 1f
                binding.workoutTime.visibility = View.VISIBLE
            }
            false -> {
                binding.schedulerSwitch.isChecked = state
                binding.schedulerText.alpha = 0.5f
                binding.workoutTime.visibility = View.GONE
            }
        }
    }

    private fun setUpToolbar() {
        requireActivity().title = getString(R.string.workout_screen)
        (requireActivity() as AppCompatActivity).supportActionBar?.setDisplayHomeAsUpEnabled(true)
        (requireActivity() as AppCompatActivity).supportActionBar?.setDisplayShowHomeEnabled(true)
    }

    override fun receive(state: WorkoutState) {
        when (state) {
            WorkoutState.Loading -> showLoading()
            is WorkoutState.DateResult -> workoutDate(state.date)
            is WorkoutState.ExceptionResult -> throw (state.exception)
            is WorkoutState.ExerciseResult -> showExercises(state.exerciseList)
            is WorkoutState.TitleResult -> workoutTitle(state.title)
            is WorkoutState.TimeResult -> workoutTime(state.time)
            is WorkoutState.SwitchState -> switchState(state.isOn)
            is WorkoutState.EndWorkoutResult -> dispatchIntent(CloseWorkout(state.workoutId.toInt()))
        }
    }

    override fun receive(event: WorkoutEvent) {
        when (event) {
            WorkoutEvent.Loading -> {}
            WorkoutEvent.ShowEditState -> showEditState()
            WorkoutEvent.SelectAllExercises -> selectAllExercises()
            WorkoutEvent.HideEditState -> hideEditState()
            WorkoutEvent.DeleteSelectedExercises -> deleteSelectedExercises()
            WorkoutEvent.DeselectAllExercises -> {}
            is WorkoutEvent.ExceptionEvent -> throw (event.exception)
            is WorkoutEvent.SetParamsDialog -> dispatchIntent(Dialog.SetParamsDialog(event.exerciseId))
        }
    }

    companion object {
        private const val KEY_PARAMS = "home_key"

        fun createBundle(data: Parcelable?): Bundle {
            requireNotNull(data)
            require(data is WorkoutScreenParams)
            return Bundle().apply { putParcelable(KEY_PARAMS, data) }
        }
    }
}
