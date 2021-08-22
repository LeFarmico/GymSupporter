package com.lefarmico.donetime.adapters

import androidx.recyclerview.widget.RecyclerView
import com.hannesdorfmann.adapterdelegates4.ListDelegationAdapter
import com.lefarmico.donetime.adapters.delegates.exerciseDelegates.CurrentExerciseButtonsDelegate
import com.lefarmico.donetime.adapters.delegates.exerciseDelegates.CurrentExerciseDelegate
import com.lefarmico.donetime.data.entities.currentExercise.ExerciseSetList
import com.lefarmico.donetime.data.entities.currentExercise.ICurrentExerciseItem
import com.lefarmico.donetime.data.entities.currentExercise.WorkoutData
import com.lefarmico.donetime.utils.ItemObserver

class CurrentExercisesAdapter(
    workoutData: WorkoutData
) : ListDelegationAdapter<List<ICurrentExerciseItem>>(), ItemObserver<ICurrentExerciseItem> {

    var onSetClick: ((ExerciseSetList) -> Unit)? = null

    init {
        delegatesManager.addDelegate(CurrentExerciseButtonsDelegate())
        delegatesManager.addDelegate(CurrentExerciseDelegate())

        workoutData.registerObserver(this)
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
        delegatesManager.onBindViewHolder(items, position, holder, null)
    }

    override fun updateData(items: MutableList<ICurrentExerciseItem>) {
        setItems(items)
    }
}
