package com.lefarmico.core.adapter.diffUtil

import android.os.Bundle
import androidx.recyclerview.widget.DiffUtil
import com.lefarmico.core.adapter.CurrentExerciseAdapter
import com.lefarmico.core.entity.CurrentWorkoutViewData

class CurrentExerciseDiffCallback(
    private val oldList: List<CurrentWorkoutViewData.ExerciseWithSets>,
    private val newList: List<CurrentWorkoutViewData.ExerciseWithSets>
) : DiffUtil.Callback() {

    override fun getOldListSize(): Int {
        return oldList.size
    }

    override fun getNewListSize(): Int {
        return newList.size
    }

    override fun areItemsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
        val oldItem = oldList[oldItemPosition]
        val newItem = newList[newItemPosition]
        return oldItem.exercise.id == newItem.exercise.id
    }

    override fun areContentsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
        val oldItem = oldList[oldItemPosition]
        val newItem = newList[newItemPosition]
        return oldItem == newItem
    }

    override fun getChangePayload(oldItemPosition: Int, newItemPosition: Int): Any? {
        val oldItem = oldList[oldItemPosition]
        val newItem = newList[newItemPosition]

        val bundle = Bundle()

        if (newItem.setList != oldItem.setList) {
            val arraySetList = ArrayList<CurrentWorkoutViewData.Set>(newItem.setList)
            bundle.putParcelableArrayList(CurrentExerciseAdapter.SET_LIST_UPDATE, arraySetList)
        }
        if (bundle.size() == 0) {
            return null
        }
        return bundle
    }
}
