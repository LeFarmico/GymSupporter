package com.lefarmico.donetime.adapters

import androidx.recyclerview.widget.RecyclerView
import com.hannesdorfmann.adapterdelegates4.ListDelegationAdapter
import com.lefarmico.domain.entity.WorkoutRecordsDto
import com.lefarmico.donetime.adapters.delegates.exerciseDelegates.CurrentExerciseDelegate
import com.lefarmico.donetime.utils.ItemObserver

class CurrentExercisesAdapter :
    ListDelegationAdapter<List<WorkoutRecordsDto>>(), ItemObserver<WorkoutRecordsDto> {

    lateinit var plusButtonCallBack: (Int) -> Unit
    lateinit var minusButtonCallback: (Int) -> Unit
    init {
        delegatesManager.addDelegate(CurrentExerciseDelegate())
    }

    override fun setItems(items: List<WorkoutRecordsDto>?) {
        super.setItems(items)
        notifyDataSetChanged()
    }

    override fun onBindViewHolder(
        holder: RecyclerView.ViewHolder,
        position: Int,
        payloads: MutableList<Any?>
    ) {
        if (holder is CurrentExerciseDelegate.ExerciseViewHolder) {
            val exercise = items[position] as WorkoutRecordsDto.Exercise
            holder.plusButton.setOnClickListener {
                plusButtonCallBack(exercise.id)
            }
            holder.minusButton.setOnClickListener {
                minusButtonCallback(exercise.id)
            }
        }
        delegatesManager.onBindViewHolder(items, position, holder, null)
    }

    override fun updateData(items: MutableList<WorkoutRecordsDto>) {
        setItems(items)
    }
}
