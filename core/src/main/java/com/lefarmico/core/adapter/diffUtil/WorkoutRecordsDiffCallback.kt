package com.lefarmico.core.adapter.diffUtil

import androidx.recyclerview.widget.DiffUtil
import com.lefarmico.core.entity.WorkoutRecordsViewData

class WorkoutRecordsDiffCallback(
    private val oldList: List<WorkoutRecordsViewData.WorkoutWithExercisesAndSets>,
    private val newList: List<WorkoutRecordsViewData.WorkoutWithExercisesAndSets>
) : DiffUtil.Callback() {
    override fun getOldListSize(): Int {
        return oldList.size
    }

    override fun getNewListSize(): Int {
        return newList.size
    }

    override fun areItemsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
        return oldList[oldItemPosition].workout.id == newList[newItemPosition].workout.id
    }

    override fun areContentsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
        return oldList[oldItemPosition] == newList[newItemPosition]
    }
}
