package com.lefarmico.presentation.adapters

import androidx.recyclerview.widget.RecyclerView
import com.hannesdorfmann.adapterdelegates4.ListDelegationAdapter
import com.lefarmico.domain.entity.LibraryDto
import com.lefarmico.presentation.adapters.delegates.exerciseLibraryDelegates.LibraryItemDelegate

class ExerciseLibraryAdapter : ListDelegationAdapter<List<LibraryDto>>() {

    lateinit var onClick: (LibraryDto) -> Unit
    init {
        delegatesManager.addDelegate(LibraryItemDelegate())
    }

    override fun setItems(items: List<LibraryDto>?) {
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
