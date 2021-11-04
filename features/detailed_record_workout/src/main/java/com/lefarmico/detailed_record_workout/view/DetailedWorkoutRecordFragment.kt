package com.lefarmico.detailed_record_workout.view

import android.os.Bundle
import android.os.Parcelable
import com.lefarmico.core.BuildConfig
import com.lefarmico.core.adapter.EditRecordAdapter
import com.lefarmico.core.base.BaseBottomSheetDialogFragment
import com.lefarmico.core.entity.WorkoutRecordsViewData
import com.lefarmico.domain.utils.DataState
import com.lefarmico.detailed_record_workout.databinding.FragmentEditWorkoutRecordBinding
import com.lefarmico.detailed_record_workout.intent.DetailedWorkoutRecordIntent
import com.lefarmico.detailed_record_workout.viewModel.DetailedWorkoutRecordViewModel
import com.lefarmico.navigation.params.RecordMenuParams

class DetailedWorkoutRecordFragment : BaseBottomSheetDialogFragment<FragmentEditWorkoutRecordBinding, DetailedWorkoutRecordViewModel>(
    FragmentEditWorkoutRecordBinding::inflate,
    DetailedWorkoutRecordViewModel::class.java
) {

    private val params: RecordMenuParams.WorkoutRecord by lazy {
        arguments?.getParcelable<RecordMenuParams.WorkoutRecord>(KEY_PARAMS)
            ?: throw (IllegalArgumentException("Arguments params must be not null"))
    }

    private val adapter = EditRecordAdapter()

    override fun setUpViews() {
        pushIntent(DetailedWorkoutRecordIntent.GetWorkout(params.workoutId))
        binding.exerciseRecycler.adapter = adapter
    }

    override fun observeData() {
        viewModel.noteWorkoutLiveData.observe(viewLifecycleOwner) { dataState ->
            when (dataState) {
                DataState.Empty -> {
                }
                is DataState.Error -> {
                }
                DataState.Loading -> {
                }
                is DataState.Success -> {
                    binding.workoutDate.text = dataState.data.workout.date
                    val itemList = mutableListOf<WorkoutRecordsViewData.ViewDataItemType>()
                    // TODO : Избавиться от логики
                    val exerciseList = dataState.data.exerciseWithSetsList
                    for (i in exerciseList.indices) {
                        itemList.add(exerciseList[i].exercise)
                        itemList.addAll(exerciseList[i].setList)
                    }
                    adapter.items = itemList
                }
            }
        }
    }

    private fun pushIntent(eventType: DetailedWorkoutRecordIntent) {
        viewModel.onTriggerEvent(eventType)
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
