package com.lefarmico.presentation.adapters.diffUtil

import androidx.recyclerview.widget.DiffUtil
import com.lefarmico.domain.entity.WorkoutRecordsDto

class WorkoutRecordsDiffCallback(
    private val oldList: List<WorkoutRecordsDto.Workout>,
    private val newList: List<WorkoutRecordsDto.Workout>
) : DiffUtil.Callback() {
    override fun getOldListSize(): Int {
        return oldList.size
    }

    override fun getNewListSize(): Int {
        return newList.size
    }

    override fun areItemsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
        return oldList[oldItemPosition].id == newList[newItemPosition].id
    }

    override fun areContentsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
        return oldList[oldItemPosition] == newList[newItemPosition]
    }
}
