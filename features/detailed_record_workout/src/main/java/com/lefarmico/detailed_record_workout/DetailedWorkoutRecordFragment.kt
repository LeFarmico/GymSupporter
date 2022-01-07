package com.lefarmico.detailed_record_workout

import android.os.Bundle
import android.os.Parcelable
import com.lefarmico.core.BuildConfig
import com.lefarmico.core.adapter.EditRecordAdapter
import com.lefarmico.core.base.BaseBottomSheetDialogFragment
import com.lefarmico.core.entity.WorkoutRecordsViewData
import com.lefarmico.detailed_record_workout.databinding.DetailedRecordFragmentBinding
import com.lefarmico.navigation.params.RecordMenuParams

class DetailedWorkoutRecordFragment :
    BaseBottomSheetDialogFragment< DetailedIntent, DetailedAction, DetailedState, DetailedEvent,
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
    }

    private fun showDate(date: String) {
        binding.workoutDate.text = date
    }

    fun showWorkoutTitle(title: String) {
        TODO("Not yet implemented")
    }

    private fun showExercises(items: MutableList<WorkoutRecordsViewData.ViewDataItemType>) {
        adapter.items = items
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

    override fun receive(state: DetailedState) {
        when (state) {
            is DetailedState.ExceptionResult -> throw (state.exception)
            DetailedState.Loading -> {}
            is DetailedState.WorkoutResult -> showWorkout(state.workout)
            is DetailedState.DateResult -> showDate(state.dateText)
        }
    }

    override fun receive(event: DetailedEvent) {
        when (event) {
            is DetailedEvent.ShowToast -> TODO()
        }
    }
}
