package com.lefarmico.core.adapter

import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.hannesdorfmann.adapterdelegates4.ListDelegationAdapter
import com.lefarmico.core.adapter.delegates.editWorkoutRecordDelegates.EditExerciseDelegate
import com.lefarmico.core.adapter.delegates.editWorkoutRecordDelegates.EditSetDelegate
import com.lefarmico.core.adapter.diffUtil.CurrentExerciseDiffCallback
import com.lefarmico.domain.entity.WorkoutRecordsDto

class EditRecordAdapter : ListDelegationAdapter<List<WorkoutRecordsDto>>() {

    lateinit var onAddSet: (Int) -> Unit
    lateinit var onDeleteSet: (Int) -> Unit
    lateinit var onDragButton: () -> Unit

    private val oldList = mutableListOf<WorkoutRecordsDto>()

    init {
        delegatesManager.addDelegate(EditExerciseDelegate())
        delegatesManager.addDelegate(EditSetDelegate())
    }

    override fun onBindViewHolder(
        holder: RecyclerView.ViewHolder,
        position: Int,
        payloads: MutableList<Any?>
    ) {
        delegatesManager.onBindViewHolder(items, position, holder, null)
        when (holder) {
            is EditExerciseDelegate.EditExerciseViewHolder -> {
                val exercise = items[position] as WorkoutRecordsDto.Exercise
//                holder.addSetButton.setOnClickListener {
//                    onAddSet(exercise.id)
//                    onDeleteSet(exercise.id)
//                }
            }
            is EditSetDelegate.EditSetViewHolder -> {
                val set = items[position] as WorkoutRecordsDto.Set
                holder.dragButton.setOnClickListener {
                    // TODO access to drag and drop
                    onDragButton()
                }
            }
        }
    }

    override fun setItems(items: List<WorkoutRecordsDto>) {
        super.setItems(items)
        val diffCallback = CurrentExerciseDiffCallback(oldList, super.items)
        val diffResult = DiffUtil.calculateDiff(diffCallback)
        diffResult.dispatchUpdatesTo(this)

        oldList.clear()
        oldList.addAll(items)
    }
}
