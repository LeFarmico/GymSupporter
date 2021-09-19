package com.lefarmico.core.adapter

import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.hannesdorfmann.adapterdelegates4.ListDelegationAdapter
import com.lefarmico.core.adapter.delegates.exerciseLibraryDelegates.LibraryItemDelegate
import com.lefarmico.core.adapter.diffUtil.ExerciseLibraryDiffCallback
import com.lefarmico.domain.entity.LibraryDto

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
