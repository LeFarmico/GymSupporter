package com.lefarmico.workout.view

import android.os.Bundle
import android.os.Parcelable
import android.view.*
import androidx.lifecycle.LiveData
import com.lefarmico.core.BuildConfig
import com.lefarmico.core.adapter.CurrentExerciseAdapter
import com.lefarmico.core.base.BaseFragment
import com.lefarmico.core.customView.StyledTextView
import com.lefarmico.core.dialog.setParameter.SetSettingDialogCallback
import com.lefarmico.core.entity.CurrentWorkoutViewData.ExerciseWithSets
import com.lefarmico.core.selector.SelectItemsHandler
import com.lefarmico.core.toolbar.EditActionBarEvents.*
import com.lefarmico.core.toolbar.RemoveActionBarCallback
import com.lefarmico.core.utils.Utilities.getAnimation
import com.lefarmico.core.utils.Utilities.getStringResource
import com.lefarmico.domain.utils.DataState
import com.lefarmico.navigation.params.WorkoutScreenParams
import com.lefarmico.navigation.params.WorkoutScreenParams.*
import com.lefarmico.workout.R
import com.lefarmico.workout.databinding.FragmentWorkoutScreenBinding
import com.lefarmico.workout.intent.WorkoutScreenIntent
import com.lefarmico.workout.intent.WorkoutScreenIntent.*
import com.lefarmico.workout.viewModel.WorkoutScreenViewModel
import java.time.LocalDate
import java.time.format.DateTimeFormatter
import java.util.*

class WorkoutScreenFragment :
    BaseFragment<FragmentWorkoutScreenBinding, WorkoutScreenViewModel>(
        FragmentWorkoutScreenBinding::inflate,
        WorkoutScreenViewModel::class.java
    ),
    SetSettingDialogCallback,
    WorkoutScreenView {

    private val adapter = CurrentExerciseAdapter()

    private var localDateTime = LocalDate.now().atStartOfDay()
    private val formatter = DateTimeFormatter.ofPattern("dd.MM.yyyy", Locale.getDefault())
    private val defTitle: String by lazy { getStringResource(R.string.default_workout_title) }

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
        setWorkoutDate(localDateTime.format(formatter))

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
            addExButton.setOnClickListener { startEvent(GoToCategoryScreen) }
            finishButton.setOnClickListener { startEvent(FinishWorkout) }
            workoutTitle.apply {
                setFactory {
                    StyledTextView(requireContext(), null, StyledTextView.HEADLINE_BOLD)
                }
                inAnimation = getAnimation(R.anim.left_slide_fade_animation)
                outAnimation = getAnimation(R.anim.right_slide_fade_animation)
                setCurrentText(defTitle)
            }
        }
        adapter.apply {
            plusButtonCallback = { launchSetParameterDialog(it) }
            minusButtonCallback = { startEvent(DeleteLastSet(it)) }
            infoButtonCallback = { startEvent(GoToExerciseInfo(it)) }
        }
    }

    override fun observeData() {
        viewModel.notificationLiveData.observe(viewLifecycleOwner) { string ->
            startEvent(ShowToast(string))
        }
        viewModel.actionBarLiveData.observe(viewLifecycleOwner) { event ->
            when (event) {
                SelectAll -> selectAllExercises()
                DeleteItems -> deleteSelectedExercises()
                Launch -> showEditState()
                Close -> hideEditState()
            }
        }
        observeLiveData(
            viewModel.exerciseLiveData,
            onSuccess = { showExercises(it) },
            onEmpty = { hideExercises() }
        )
        observeLiveData(
            viewModel.setParametersDialogLiveData,
            onSuccess = {
                startEvent(
                    ShowSetParametersDialog(
                        parentFragmentManager,
                        it.toInt(),
                        this,
                        TAG_DIALOG
                    )
                )
            }
        )
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

    override fun showLoading() {
        binding.state.showLoadingState()
    }

    override fun showSuccess() {
        binding.state.showSuccessState()
    }

    override fun showError(e: Exception) {
        binding.state.showErrorState()
        e.printStackTrace()
    }

    override fun showEmpty() {
        binding.state.showEmptyState()
    }

    override fun showExercises(items: List<ExerciseWithSets>) {
        adapter.items = items
    }

    override fun hideExercises() {
        adapter.items = listOf()
    }

    override fun showEditState() {
        adapter.turnOnEditState()
        actionMode = requireActivity().startActionMode(actionModeCallback)
    }

    override fun hideEditState() {
        adapter.turnOffEditState()
        actionMode?.finish()
    }

    override fun setWorkoutTitle(title: String) {
        binding.workoutTitle.setText(title)
    }

    override fun setWorkoutDate(date: String) {
        binding.workoutDate.text = date
    }

    private fun deleteSelectedExercises() {
        selectHandler?.onEachSelectedItemsAction()
        actionMode?.finish()
    }

    private fun selectAllExercises() {
        adapter.toggleSelectAll()
    }

    private fun <T> observeLiveData(
        liveData: LiveData<DataState<T>>,
        onEmpty: () -> Unit = {},
        onLoading: () -> Unit = {},
        onError: (Exception) -> Unit = { it.printStackTrace() },
        onSuccess: (T) -> Unit = {}
    ) {
        liveData.observe(viewLifecycleOwner) { dataState ->
            when (dataState) {
                DataState.Empty -> {
                    onEmpty()
                    showEmpty()
                }
                is DataState.Error -> {
                    onError(dataState.exception)
                    showError(dataState.exception)
                }
                DataState.Loading -> {
                    onLoading()
                    showLoading()
                }
                is DataState.Success -> {
                    onSuccess(dataState.data)
                    showSuccess()
                }
            }
        }
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
