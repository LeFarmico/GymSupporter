package com.lefarmico.core.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.lefarmico.core.databinding.ItemEditRecordExerciseBinding
import com.lefarmico.core.databinding.ItemEditRecordSetBinding
import com.lefarmico.core.entity.WorkoutRecordsViewData

class EditRecordAdapter : RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    var items = listOf<WorkoutRecordsViewData.ViewDataItemType>()
        set(value) {
            field = value
            notifyItemRangeChanged(0, field.size)
        }

    class EditExerciseViewHolder(
        itemEditRecordBinding: ItemEditRecordExerciseBinding
    ) : RecyclerView.ViewHolder(itemEditRecordBinding.root) {

        private val exerciseTitle = itemEditRecordBinding.exerciseTitle

        fun bind(title: String) {
            exerciseTitle.text = title
        }
    }

    class EditSetViewHolder(
        itemEditRecordSetBinding: ItemEditRecordSetBinding
    ) : RecyclerView.ViewHolder(itemEditRecordSetBinding.root) {

        private val setNumber = itemEditRecordSetBinding.set.setNumber
        private val reps = itemEditRecordSetBinding.set.reps
        private val weight = itemEditRecordSetBinding.set.weight

        fun bind(setNumber: Int, repsCount: Int, weight: Float) {
            this.setNumber.text = setNumber.toString()
            this.reps.text = repsCount.toString()
            this.weight.text = weight.toString()
        }
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        val item = items[position]
        when (item.getItemType()) {
            WorkoutRecordsViewData.EXERCISE -> {
                holder as EditExerciseViewHolder
                item as WorkoutRecordsViewData.Exercise
                holder.bind(item.exerciseName)
            }
            WorkoutRecordsViewData.SET -> {
                holder as EditSetViewHolder
                item as WorkoutRecordsViewData.Set
                holder.bind(
                    item.setNumber,
                    item.reps,
                    item.weight
                )
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return when (viewType) {
            WorkoutRecordsViewData.EXERCISE -> {
                EditExerciseViewHolder(
                    ItemEditRecordExerciseBinding.inflate(
                        LayoutInflater.from(parent.context), parent, false
                    )
                )
            }
            WorkoutRecordsViewData.SET -> {
                EditSetViewHolder(
                    ItemEditRecordSetBinding.inflate(
                        LayoutInflater.from(parent.context), parent, false
                    )
                )
            }
            else -> {
                throw (IllegalArgumentException("Not existing ViewDataItemType: $viewType"))
            }
        }
    }

    override fun getItemCount(): Int {
        return items.size
    }

    override fun getItemViewType(position: Int): Int {
        return try {
            (items[position]).getItemType()
        } catch (e: ClassCastException) {
            throw ClassCastException("items must resolve ViewDataItemType interface")
        }
    }
}
