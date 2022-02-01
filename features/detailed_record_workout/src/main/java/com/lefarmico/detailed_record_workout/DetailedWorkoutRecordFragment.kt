package com.lefarmico.detailed_record_workout

import android.os.Bundle
import android.os.Parcelable
import android.view.View
import com.lefarmico.core.adapter.EditRecordAdapter
import com.lefarmico.core.base.BaseBottomSheetDialogFragment
import com.lefarmico.core.entity.WorkoutRecordsViewData
import com.lefarmico.detailed_record_workout.databinding.DetailedRecordsFragmentBinding
import com.lefarmico.navigation.params.RecordMenuParams

class DetailedWorkoutRecordFragment :
    BaseBottomSheetDialogFragment< DetailedIntent, DetailedState, DetailedEvent,
        DetailedRecordsFragmentBinding, DetailedWorkoutRecordViewModel>(
        DetailedRecordsFragmentBinding::inflate,
        DetailedWorkoutRecordViewModel::class.java
    ) {
    private val params: RecordMenuParams.WorkoutRecord by lazy {
        arguments?.getParcelable<RecordMenuParams.WorkoutRecord>(KEY_PARAMS)
            ?: throw (IllegalArgumentException("Arguments params must be not null"))
    }

    private val adapter = EditRecordAdapter()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        dispatchIntent(DetailedIntent.GetWorkout(params.workoutId))
    }

    override fun setUpViews() {
        binding.exerciseRecycler.adapter = adapter

        binding.editButton.setOnClickListener {
            dispatchIntent(DetailedIntent.EditWorkout(params.workoutId))
        }
        binding.cancelButton.setOnClickListener {
            dismiss()
        }
    }

    override fun receive(state: DetailedState) {
        when (state) {
            is DetailedState.WorkoutResult -> showWorkout(state.workout, state.exercises)
            is DetailedState.DateResult -> showDate(state.dateText)
            is DetailedState.TitleResult -> showWorkoutTitle(state.title)
        }
    }

    override fun receive(event: DetailedEvent) {
        when (event) {
            DetailedEvent.DataLoadFailure -> closeWithError(getString(R.string.state_error))
            DetailedEvent.Loading -> loading(true)
            is DetailedEvent.ExceptionResult -> onExceptionResult(event.exception)
        }
    }

    private fun showWorkout(
        workout: WorkoutRecordsViewData.Workout,
        exercises: List<WorkoutRecordsViewData.ViewDataItemType>
    ) {
        loading(false)

        showExercises(exercises)
        showDate(workout.date)
        showWorkoutTitle(workout.title)
        showTime(workout.time)
    }

    private fun showDate(date: String) {
        binding.workoutDate.text = date
    }

    private fun showWorkoutTitle(title: String) {
        binding.workoutTitle.text = title
    }

    private fun showTime(time: String) {
        if (time == "") {
            binding.workoutTime.visibility = View.GONE
        } else {
            binding.workoutTime.text = time
            binding.workoutTime.visibility = View.VISIBLE
        }
    }

    private fun closeWithError(text: String) {
        dispatchIntent(DetailedIntent.CloseWithToast(text))
    }

    private fun onExceptionResult(exception: Exception) {
        // TODO send log to crashlytics
    }

    private fun showExercises(items: List<WorkoutRecordsViewData.ViewDataItemType>) {
        adapter.items = items
    }

    private fun loading(show: Boolean) {
        when (show) {
            true -> binding.progressBar.visibility = View.VISIBLE
            false -> binding.progressBar.visibility = View.GONE
        }
    }

    companion object {
        private const val KEY_PARAMS = "edit_key"

        fun createBundle(data: Parcelable?): Bundle {
            requireNotNull(data)
            require(data is RecordMenuParams.WorkoutRecord)
            return Bundle().apply { putParcelable(KEY_PARAMS, data) }
        }
    }
}
