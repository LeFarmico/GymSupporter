package com.lefarmico.donetime.adapters

import androidx.recyclerview.widget.RecyclerView
import com.hannesdorfmann.adapterdelegates4.ListDelegationAdapter
import com.lefarmico.donetime.adapters.delegates.exerciseDelegates.CurrentExerciseButtonsDelegate
import com.lefarmico.donetime.adapters.delegates.exerciseDelegates.CurrentExerciseNameDelegate
import com.lefarmico.donetime.adapters.delegates.exerciseDelegates.CurrentExerciseSetDelegate
import com.lefarmico.donetime.data.entities.exercise.ExerciseDataManager
import com.lefarmico.donetime.data.entities.exercise.ExerciseMuscleSetEntity
import com.lefarmico.donetime.data.models.ICurrentExerciseItem
import com.lefarmico.donetime.utils.ItemObserver

class CurrentExercisesAdapter(
    exerciseDataManager: ExerciseDataManager
) : ListDelegationAdapter<List<ICurrentExerciseItem>>(), ItemObserver<ICurrentExerciseItem> {

    init {
        delegatesManager.addDelegate(CurrentExerciseNameDelegate())
        delegatesManager.addDelegate(CurrentExerciseButtonsDelegate())
        delegatesManager.addDelegate(CurrentExerciseSetDelegate())

        exerciseDataManager.registerObserver(this)
    }

    override fun setItems(items: List<ICurrentExerciseItem>?) {
        super.setItems(items)
        notifyDataSetChanged()
    }

    override fun onBindViewHolder(
        holder: RecyclerView.ViewHolder,
        position: Int,
        payloads: MutableList<Any?>
    ) {
        val cornerPayload = bindRoundCorners(position)
        if (items[position] is ExerciseMuscleSetEntity) {
            delegatesManager.onBindViewHolder(items, position, holder, mutableListOf(cornerPayload))
        } else {
            delegatesManager.onBindViewHolder(items, position, holder, null)
        }
    }

    private fun bindRoundCorners(position: Int): Int {
        val isPrevExercise: Boolean = try {
            items[position - 1] is ExerciseMuscleSetEntity
        } catch (e: IndexOutOfBoundsException) { false }
        val isNextExercise: Boolean = try {
            items[position + 1] is ExerciseMuscleSetEntity
        } catch (e: IndexOutOfBoundsException) { false }

        return if (!isPrevExercise) {
            if (isNextExercise) {
                CurrentExerciseSetDelegate.TOP_CORNER
            } else {
                CurrentExerciseSetDelegate.SINGLE_CORNER
            }
        } else {
            if (isNextExercise) {
                CurrentExerciseSetDelegate.MIDDLE_CORNER
            } else {
                CurrentExerciseSetDelegate.BOTTOM_CORNER
            }
        }
    }

    override fun updateData(items: MutableList<ICurrentExerciseItem>) {
        setItems(items)
    }
}
