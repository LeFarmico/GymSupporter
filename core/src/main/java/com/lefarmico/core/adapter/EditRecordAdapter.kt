package com.lefarmico.core.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.lefarmico.core.R
import com.lefarmico.core.databinding.ItemEditRecordExerciseBinding
import com.lefarmico.core.databinding.ItemExerciseSetBinding
import com.lefarmico.core.entity.WorkoutRecordsViewData

class EditRecordAdapter : RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    var items = listOf<WorkoutRecordsViewData.ViewDataItemType>()
        set(value) {
            field = value
            notifyDataSetChanged()
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
        itemExerciseSetBinding: ItemExerciseSetBinding
    ) : RecyclerView.ViewHolder(itemExerciseSetBinding.root) {

        private val context = itemExerciseSetBinding.reps.context
        private val setNumber: TextView = itemExerciseSetBinding.setNumber
        private val weights: TextView = itemExerciseSetBinding.weight
        private var reps = itemExerciseSetBinding.reps

        fun bind(item: WorkoutRecordsViewData.Set) {
            setNumber.text = context.getString(R.string.set_field, item.setNumber)
            weights.text = context.getString(R.string.weight_field, item.weight.toString())
            reps.text = context.getString(R.string.repetitions_field, item.reps)
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
                holder.bind(item)
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
                    ItemExerciseSetBinding.inflate(
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
