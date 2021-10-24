package com.lefarmico.core.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.lefarmico.core.adapter.diffUtil.SetRecordsDiffCallback
import com.lefarmico.core.databinding.ItemExerciseSetBinding
import com.lefarmico.core.entity.WorkoutRecordsViewData

class SetRecordsAdapter : RecyclerView.Adapter<SetRecordsAdapter.SetViewHolder>() {

    class SetViewHolder(
        itemExerciseSetBinding: ItemExerciseSetBinding
    ) : RecyclerView.ViewHolder(itemExerciseSetBinding.root) {

        private var reps = itemExerciseSetBinding.reps
        private var setNumber = itemExerciseSetBinding.setNumber
        private var wieght = itemExerciseSetBinding.weight

        fun bind(noteSet: WorkoutRecordsViewData.Set) {
            reps.text = noteSet.reps.toString()
            wieght.text = noteSet.weight.toString()
        }
        fun bindSetNumber(number: Int) {
            setNumber.text = number.toString()
        }
    }
    var items = listOf<WorkoutRecordsViewData.Set>()
        set(value) {
            val oldList = field
            field = value
            val diffCallback = SetRecordsDiffCallback(oldList, field)
            val diffResult = DiffUtil.calculateDiff(diffCallback)
            diffResult.dispatchUpdatesTo(this)
        }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): SetViewHolder {
        return SetViewHolder(
            ItemExerciseSetBinding.inflate(
                LayoutInflater.from(parent.context), parent, false
            )
        )
    }

    override fun onBindViewHolder(holder: SetViewHolder, position: Int) {
        holder.bind(items[position])
        holder.bindSetNumber(position + 1)
    }

    override fun getItemCount(): Int {
        return items.size
    }
}
