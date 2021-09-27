package com.lefarmico.core.adapter.delegates.editWorkoutRecordDelegates

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.hannesdorfmann.adapterdelegates4.AbsListItemAdapterDelegate
import com.lefarmico.core.databinding.ItemEditRecordSetBinding
import com.lefarmico.domain.entity.WorkoutRecordsDto

class EditSetDelegate : AbsListItemAdapterDelegate<
    WorkoutRecordsDto.Set,
    WorkoutRecordsDto,
    EditSetDelegate.EditSetViewHolder>() {

    class EditSetViewHolder(
        itemEditRecordSetBinding: ItemEditRecordSetBinding
    ) : RecyclerView.ViewHolder(itemEditRecordSetBinding.root) {

        private val setNumber = itemEditRecordSetBinding.set.setNumber
        private val reps = itemEditRecordSetBinding.set.reps
        private val weight = itemEditRecordSetBinding.set.weight

        val dragButton = itemEditRecordSetBinding.dragAndDropPoint

        fun bind(setNumber: Int, repsCount: Int, weight: Float) {
            this.setNumber.text = setNumber.toString()
            this.reps.text = repsCount.toString()
            this.weight.text = weight.toString()
        }
    }

    override fun isForViewType(
        item: WorkoutRecordsDto,
        items: MutableList<WorkoutRecordsDto>,
        position: Int
    ): Boolean {
        return item is WorkoutRecordsDto.Set
    }

    override fun onCreateViewHolder(parent: ViewGroup): EditSetViewHolder {
        return EditSetViewHolder(
            ItemEditRecordSetBinding.inflate(
                LayoutInflater.from(parent.context), parent, false
            )
        )
    }

    override fun onBindViewHolder(
        item: WorkoutRecordsDto.Set,
        holder: EditSetViewHolder,
        payloads: MutableList<Any>
    ) {
        holder.bind(
            item.setNumber,
            item.reps,
            item.weight
        )
    }
}
