package com.lefarmico.core.adapter.diffUtil

import androidx.recyclerview.widget.DiffUtil
import com.lefarmico.domain.entity.WorkoutRecordsDto

class CurrentExerciseDiffCallback(
    private val oldList: List<WorkoutRecordsDto>,
    private val newList: List<WorkoutRecordsDto>
) : DiffUtil.Callback() {

    override fun getOldListSize(): Int {
        return oldList.size
    }

    override fun getNewListSize(): Int {
        return newList.size
    }

    override fun areItemsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
        when (oldList[oldItemPosition]) {
            is WorkoutRecordsDto.Exercise -> {
                return if (newList[newItemPosition] is WorkoutRecordsDto.Exercise) {
                    val oldItem = oldList[oldItemPosition] as WorkoutRecordsDto.Exercise
                    val newItem = newList[newItemPosition] as WorkoutRecordsDto.Exercise
                    oldItem.id == newItem.id
                } else {
                    false
                }
            }
            is WorkoutRecordsDto.Set -> {
                return if (newList[newItemPosition] is WorkoutRecordsDto.Set) {
                    val oldItem = oldList[oldItemPosition] as WorkoutRecordsDto.Set
                    val newItem = newList[newItemPosition] as WorkoutRecordsDto.Set
                    oldItem.id == newItem.id
                } else {
                    false
                }
            }
            is WorkoutRecordsDto.Workout -> {
                return if (newList[newItemPosition] is WorkoutRecordsDto.Workout) {
                    val oldItem = oldList[oldItemPosition] as WorkoutRecordsDto.Workout
                    val newItem = newList[newItemPosition] as WorkoutRecordsDto.Workout
                    oldItem.id == newItem.id
                } else {
                    false
                }
            }
        }
    }

    override fun areContentsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
        when (oldList[oldItemPosition]) {
            is WorkoutRecordsDto.Exercise -> {
                return if (newList[newItemPosition] is WorkoutRecordsDto.Exercise) {
                    val oldItem = oldList[oldItemPosition] as WorkoutRecordsDto.Exercise
                    val newItem = newList[newItemPosition] as WorkoutRecordsDto.Exercise
                    oldItem == newItem
                } else {
                    false
                }
            }
            is WorkoutRecordsDto.Set -> {
                return if (newList[newItemPosition] is WorkoutRecordsDto.Set) {
                    val oldItem = oldList[oldItemPosition] as WorkoutRecordsDto.Set
                    val newItem = newList[newItemPosition] as WorkoutRecordsDto.Set
                    oldItem == newItem
                } else {
                    false
                }
            }
            is WorkoutRecordsDto.Workout -> {
                return if (newList[newItemPosition] is WorkoutRecordsDto.Workout) {
                    val oldItem = oldList[oldItemPosition] as WorkoutRecordsDto.Workout
                    val newItem = newList[newItemPosition] as WorkoutRecordsDto.Workout
                    oldItem == newItem
                } else {
                    false
                }
            }
        }
    }
}
