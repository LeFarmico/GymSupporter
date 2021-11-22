package com.lefarmico.core.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.lefarmico.core.R
import com.lefarmico.core.adapter.diffUtil.SetRecordsDiffCallback
import com.lefarmico.core.databinding.ItemExerciseSetBinding
import com.lefarmico.core.entity.WorkoutRecordsViewData

class SetRecordsAdapter : RecyclerView.Adapter<SetRecordsAdapter.SetViewHolder>() {

    class SetViewHolder(
        itemExerciseSetBinding: ItemExerciseSetBinding
    ) : RecyclerView.ViewHolder(itemExerciseSetBinding.root) {

        private val context = itemExerciseSetBinding.reps.context
        private val setNumber: TextView = itemExerciseSetBinding.setNumber
        private val weights: TextView = itemExerciseSetBinding.weight
        private var reps = itemExerciseSetBinding.reps

        fun bind(noteSet: WorkoutRecordsViewData.Set) {
            reps.text = context.getString(R.string.repetitions_field, noteSet.reps)
            weights.text = context.getString(R.string.weight_field, noteSet.weight.toString())
        }
        fun bindSetNumber(number: Int) {
            setNumber.text = context.getString(R.string.set_field, number)
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
