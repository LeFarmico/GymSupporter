package com.lefarmico.detailed_record_workout

import android.os.Bundle
import android.os.Parcelable
import android.view.View
import com.lefarmico.core.BuildConfig
import com.lefarmico.core.adapter.EditRecordAdapter
import com.lefarmico.core.base.BaseBottomSheetDialogFragment
import com.lefarmico.core.entity.WorkoutRecordsViewData
import com.lefarmico.detailed_record_workout.databinding.DetailedRecordFragmentBinding
import com.lefarmico.navigation.params.RecordMenuParams

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

    private fun showExercises(items: MutableList<WorkoutRecordsViewData.ViewDataItemType>) {
        adapter.items = items
    }

    override fun receive(state: DetailedState) {
        when (state) {
            is DetailedState.ExceptionResult -> throw (state.exception)
            DetailedState.Loading -> {}
            is DetailedState.WorkoutResult -> showWorkout(state.workout)
            is DetailedState.DateResult -> showDate(state.dateText)
            is DetailedState.TitleResult -> showWorkoutTitle(state.title)
        }
    }

    override fun receive(event: DetailedEvent) {
        when (event) {
            is DetailedEvent.ShowToast -> TODO()
        }
    }

    companion object {
        private const val KEY_PARAMS = "edit_key"

        fun createBundle(data: Parcelable?): Bundle {
            return Bundle().apply {
                when (data) {
                    is RecordMenuParams.WorkoutRecord -> putParcelable(KEY_PARAMS, data)
                    else -> {
                        if (BuildConfig.DEBUG) {
                            throw (
                                IllegalArgumentException(
                                    "data should be RecordMenuParams.WorkoutRecord type." +
                                        "but it's type ${data!!.javaClass.canonicalName}"
                                )
                                )
                        }
                    }
                }
            }
        }
    }
}
