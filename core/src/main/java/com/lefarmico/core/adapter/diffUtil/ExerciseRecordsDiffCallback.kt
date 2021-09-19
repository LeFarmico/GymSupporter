package com.lefarmico.core.adapter.diffUtil

import androidx.recyclerview.widget.DiffUtil
import com.lefarmico.domain.entity.WorkoutRecordsDto

class ExerciseRecordsDiffCallback(
    private val oldList: List<WorkoutRecordsDto.Exercise>,
    private val newList: List<WorkoutRecordsDto.Exercise>
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
