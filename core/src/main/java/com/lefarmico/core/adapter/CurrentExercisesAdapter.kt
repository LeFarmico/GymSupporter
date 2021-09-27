package com.lefarmico.core.adapter

import androidx.recyclerview.widget.RecyclerView
import com.hannesdorfmann.adapterdelegates4.ListDelegationAdapter
import com.lefarmico.core.adapter.delegates.exerciseDelegates.CurrentExerciseDelegate
import com.lefarmico.domain.entity.WorkoutRecordsDto

class CurrentExercisesAdapter :
    ListDelegationAdapter<List<WorkoutRecordsDto>>() {

    lateinit var onSelectExercise: (Int) -> Unit
    lateinit var plusButtonCallBack: (Int) -> Unit
    lateinit var minusButtonCallback: (Int) -> Unit
    private val oldList = mutableListOf<WorkoutRecordsDto>()

    init {
        delegatesManager.addDelegate(CurrentExerciseDelegate())
    }

    override fun setItems(items: List<WorkoutRecordsDto>) {
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
            holder.rootLayout.setOnClickListener {
                onSelectExercise(exercise.id)
            }
        }
        delegatesManager.onBindViewHolder(items, position, holder, null)
    }
}
