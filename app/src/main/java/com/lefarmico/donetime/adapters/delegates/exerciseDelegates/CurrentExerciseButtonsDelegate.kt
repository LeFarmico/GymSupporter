package com.lefarmico.donetime.adapters.delegates.exerciseDelegates

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.hannesdorfmann.adapterdelegates4.AbsListItemAdapterDelegate
import com.lefarmico.donetime.data.entities.currentExercise.ExerciseButtons
import com.lefarmico.donetime.data.entities.currentExercise.ICurrentExerciseItem
import com.lefarmico.donetime.databinding.ItemAddButtonBinding

class CurrentExerciseButtonsDelegate : AbsListItemAdapterDelegate<
    ExerciseButtons,
    ICurrentExerciseItem,
    CurrentExerciseButtonsDelegate.ButtonViewHolder
    >() {
    
    class ButtonViewHolder(
        itemAddButtonBinding: ItemAddButtonBinding
    ) : RecyclerView.ViewHolder(itemAddButtonBinding.root) {
        private val addButton = itemAddButtonBinding.addButton
        private val deleteButton = itemAddButtonBinding.deleteButton

        fun bind(item: ExerciseButtons) {
            addButton.setOnClickListener {
                item.addButtonCallback()
            }
            deleteButton.setOnClickListener {
                item.deleteButtonCallback()
            }
        }
    }
    
    override fun isForViewType(
        item: ICurrentExerciseItem,
        items: MutableList<ICurrentExerciseItem>,
        position: Int
    ): Boolean {
        return item is ExerciseButtons
    }

    override fun onCreateViewHolder(parent: ViewGroup): ButtonViewHolder {
        return ButtonViewHolder(
            ItemAddButtonBinding.inflate(
                LayoutInflater.from(parent.context), parent, false
            )
        )
    }

    override fun onBindViewHolder(
        item: ExerciseButtons,
        holder: ButtonViewHolder,
        payloads: MutableList<Any>
    ) {
        holder.bind(item)
    }
}
