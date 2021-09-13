package com.lefarmico.presentation.adapters

import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.hannesdorfmann.adapterdelegates4.ListDelegationAdapter
import com.lefarmico.domain.entity.LibraryDto
import com.lefarmico.presentation.adapters.delegates.exerciseLibraryDelegates.LibraryItemDelegate
import com.lefarmico.presentation.adapters.diffUtil.ExerciseLibraryDiffCallback

class ExerciseLibraryAdapter : ListDelegationAdapter<List<LibraryDto>>() {

    lateinit var onClick: (LibraryDto) -> Unit
    init {
        delegatesManager.addDelegate(LibraryItemDelegate())
    }

    override fun setItems(items: List<LibraryDto>?) {
        val oldItems = super.items ?: listOf()
        super.setItems(items)
        val diffCallback = ExerciseLibraryDiffCallback(oldItems, super.items)
        val diffResult = DiffUtil.calculateDiff(diffCallback)
        diffResult.dispatchUpdatesTo(this)
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
