package com.lefarmico.donetime.adapters

import androidx.recyclerview.widget.RecyclerView
import com.hannesdorfmann.adapterdelegates4.ListDelegationAdapter
import com.lefarmico.donetime.adapters.delegates.exerciseLibraryDelegates.ExerciseLibraryCategoryItem
import com.lefarmico.donetime.data.models.IExerciseLibraryItem

class ExerciseLibraryAdapter : ListDelegationAdapter<List<IExerciseLibraryItem>>() {

    lateinit var onClick: (IExerciseLibraryItem) -> Unit
    init {
        delegatesManager.addDelegate(ExerciseLibraryCategoryItem())
    }

    override fun setItems(items: List<IExerciseLibraryItem>?) {
        super.setItems(items)
        notifyDataSetChanged()
    }

    override fun onBindViewHolder(
        holder: RecyclerView.ViewHolder,
        position: Int,
        payloads: MutableList<Any?>
    ) {
        holder.itemView.setOnClickListener {
            onClick(items[position])
        }
        delegatesManager.onBindViewHolder(items, position, holder, null)
    }
}
