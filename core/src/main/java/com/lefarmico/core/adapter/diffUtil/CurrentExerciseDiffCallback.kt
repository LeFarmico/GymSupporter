package com.lefarmico.core.adapter.diffUtil

import androidx.recyclerview.widget.DiffUtil
import com.lefarmico.core.entity.WorkoutRecordsViewData

class CurrentExerciseDiffCallback(
    private val oldList: List<WorkoutRecordsViewData.ViewDataItemType>,
    private val newList: List<WorkoutRecordsViewData.ViewDataItemType>
) : DiffUtil.Callback() {

    override fun getOldListSize(): Int {
        return oldList.size
    }

    override fun getNewListSize(): Int {
        return newList.size
    }

    override fun areItemsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
        when (oldList[oldItemPosition]) {
            is WorkoutRecordsViewData.Exercise -> {
                return if (newList[newItemPosition] is WorkoutRecordsViewData.Exercise) {
                    val oldItem = oldList[oldItemPosition] as WorkoutRecordsViewData.Exercise
                    val newItem = newList[newItemPosition] as WorkoutRecordsViewData.Exercise
                    oldItem.id == newItem.id
                } else {
                    false
                }
            }
            is WorkoutRecordsViewData.Set -> {
                return if (newList[newItemPosition] is WorkoutRecordsViewData.Set) {
                    val oldItem = oldList[oldItemPosition] as WorkoutRecordsViewData.Set
                    val newItem = newList[newItemPosition] as WorkoutRecordsViewData.Set
                    oldItem.id == newItem.id
                } else {
                    false
                }
            }
            else -> {
                throw (TypeCastException("Object should resolve ViewDataItemType interface"))
            }
        }
    }

    override fun areContentsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
        when (oldList[oldItemPosition]) {
            is WorkoutRecordsViewData.Exercise -> {
                return if (newList[newItemPosition] is WorkoutRecordsViewData.Exercise) {
                    val oldItem = oldList[oldItemPosition] as WorkoutRecordsViewData.Exercise
                    val newItem = newList[newItemPosition] as WorkoutRecordsViewData.Exercise
                    oldItem == newItem
                } else {
                    false
                }
            }
            is WorkoutRecordsViewData.Set -> {
                return if (newList[newItemPosition] is WorkoutRecordsViewData.Set) {
                    val oldItem = oldList[oldItemPosition] as WorkoutRecordsViewData.Set
                    val newItem = newList[newItemPosition] as WorkoutRecordsViewData.Set
                    oldItem == newItem
                } else {
                    false
                }
            }
            else -> {
                throw (TypeCastException("Object should resolve ViewDataItemType interface"))
            }
        }
    }
}
