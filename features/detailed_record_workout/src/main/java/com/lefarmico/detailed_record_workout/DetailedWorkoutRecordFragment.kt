package com.lefarmico.detailed_record_workout

import android.os.Bundle
import android.os.Parcelable
import android.view.View
import com.lefarmico.core.adapter.EditRecordAdapter
import com.lefarmico.core.base.BaseBottomSheetDialogFragment
import com.lefarmico.core.entity.WorkoutRecordsViewData
import com.lefarmico.detailed_record_workout.databinding.DetailedRecordFragmentBinding
import com.lefarmico.navigation.params.RecordMenuParams
import java.lang.Exception

class DetailedWorkoutRecordFragment :
    BaseBottomSheetDialogFragment< DetailedIntent, DetailedState, DetailedEvent,
        DetailedRecordFragmentBinding, DetailedWorkoutRecordViewModel>(
        DetailedRecordFragmentBinding::inflate,
        DetailedWorkoutRecordViewModel::class.java
    ) {

    private val params: RecordMenuParams.WorkoutRecord by lazy {
        arguments?.getParcelable<RecordMenuParams.WorkoutRecord>(KEY_PARAMS)
            ?: throw (IllegalArgumentException("Arguments params must be not null"))
    }

    private val adapter = EditRecordAdapter()

    override fun setUpViews() {
        dispatchIntent(DetailedIntent.GetWorkout(params.workoutId))
        binding.exerciseRecycler.adapter = adapter
        binding.acceptButton.setOnClickListener {
            dispatchIntent(DetailedIntent.EditWorkout(params.workoutId))
        }
        binding.cancelButton.setOnClickListener {
            dismiss()
        }
    }

    private fun showWorkout(workout: WorkoutRecordsViewData.WorkoutWithExercisesAndSets) {

        val itemList = mutableListOf<WorkoutRecordsViewData.ViewDataItemType>()
        // TODO : Избавиться от логики
        val exerciseList = workout.exerciseWithSetsList
        for (i in exerciseList.indices) {
            itemList.add(exerciseList[i].exercise)
            itemList.addAll(exerciseList[i].setList)
        }
        showExercises(itemList)
        showDate(workout.workout.date)
        showWorkoutTitle(workout.workout.title)
        showTime(workout.workout.time)
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

    private fun logException(exception: Exception) {
        // TODO send log to crashlytics
    }

    private fun showExercises(items: MutableList<WorkoutRecordsViewData.ViewDataItemType>) {
        adapter.items = items
    }

    override fun receive(state: DetailedState) {
        when (state) {
            is DetailedState.ExceptionResult -> logException(state.exception)
            is DetailedState.WorkoutResult -> showWorkout(state.workout)
            is DetailedState.DateResult -> showDate(state.dateText)
            is DetailedState.TitleResult -> showWorkoutTitle(state.title)
        }
    }

    override fun receive(event: DetailedEvent) {
        when (event) {
            DetailedEvent.DataLoadFailure -> closeWithError(getString(R.string.error_state))
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
