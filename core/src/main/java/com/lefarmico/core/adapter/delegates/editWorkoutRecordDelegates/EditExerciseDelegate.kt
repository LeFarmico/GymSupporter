package com.lefarmico.core.adapter.delegates.editWorkoutRecordDelegates

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.hannesdorfmann.adapterdelegates4.AbsListItemAdapterDelegate
import com.lefarmico.core.databinding.ItemEditRecordExerciseBinding
import com.lefarmico.core.entity.WorkoutRecordsViewData

class EditExerciseDelegate : AbsListItemAdapterDelegate<
    WorkoutRecordsViewData.Exercise,
    WorkoutRecordsViewData,
    EditExerciseDelegate.EditExerciseViewHolder>() {

    class EditExerciseViewHolder(
        itemEditRecordBinding: ItemEditRecordExerciseBinding
    ) : RecyclerView.ViewHolder(itemEditRecordBinding.root) {

        private val exerciseTitle = itemEditRecordBinding.exerciseTitle
//        val addSetButton = itemEditRecordBinding.plusButton
//        val deleteSetButton = itemEditRecordBinding.minusButton

        fun bind(title: String) {
            exerciseTitle.text = title
        }
    }

    override fun isForViewType(
        item: WorkoutRecordsViewData,
        items: MutableList<WorkoutRecordsViewData>,
        position: Int
    ): Boolean {
        return item is WorkoutRecordsViewData.ExerciseWithSets
    }

    override fun onCreateViewHolder(parent: ViewGroup): EditExerciseViewHolder {
        return EditExerciseViewHolder(
            ItemEditRecordExerciseBinding.inflate(
                LayoutInflater.from(parent.context), parent, false
            )
        )
    }

    override fun onBindViewHolder(
        item: WorkoutRecordsViewData.Exercise,
        holder: EditExerciseViewHolder,
        payloads: MutableList<Any>
    ) {
        holder.bind(item.exerciseName)
    }
}
