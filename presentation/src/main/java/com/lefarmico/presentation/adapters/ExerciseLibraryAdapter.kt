package com.lefarmico.donetime.adapters

import androidx.recyclerview.widget.RecyclerView
import com.hannesdorfmann.adapterdelegates4.ListDelegationAdapter
import com.lefarmico.donetime.adapters.delegates.exerciseLibraryDelegates.LibraryItemDelegate
import com.lefarmico.donetime.data.entities.library.ILibraryItem

class ExerciseLibraryAdapter : ListDelegationAdapter<List<ILibraryItem>>() {

    lateinit var onClick: (ILibraryItem) -> Unit
    init {
        delegatesManager.addDelegate(LibraryItemDelegate())
    }

    override fun setItems(items: List<ILibraryItem>?) {
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
