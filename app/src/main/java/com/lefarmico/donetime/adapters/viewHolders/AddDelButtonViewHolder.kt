package com.lefarmico.donetime.adapters.viewHolders

import com.lefarmico.donetime.adapters.exercise.entity.AddDelButtons
import com.lefarmico.donetime.databinding.ItemAddButtonBinding
import com.lefarmico.lerecycle.ItemType
import com.lefarmico.lerecycle.LeRecyclerViewHolder

class AddDelButtonViewHolder(
    itemAddButtonBinding: ItemAddButtonBinding
) : LeRecyclerViewHolder<ItemType>(itemAddButtonBinding.root) {

    private val addButton = itemAddButtonBinding.addButton
    private val deleteButton = itemAddButtonBinding.deleteButton

    override fun bind(item: ItemType, position: Int, itemCount: Int) {
        item as AddDelButtons
        addButton.setOnClickListener {
            item.addButtonCallback
        }
        deleteButton.setOnClickListener {
            item.deleteButtonCallback
        }
    }
}