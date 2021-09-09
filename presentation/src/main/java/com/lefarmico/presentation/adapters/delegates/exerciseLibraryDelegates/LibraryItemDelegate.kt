package com.lefarmico.presentation.adapters.delegates.exerciseLibraryDelegates

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.hannesdorfmann.adapterdelegates4.AbsListItemAdapterDelegate
import com.lefarmico.domain.entity.LibraryDto
import com.lefarmico.presentation.databinding.ItemLibraryListBinding

class LibraryItemDelegate : AbsListItemAdapterDelegate<
    LibraryDto,
    LibraryDto,
    LibraryItemDelegate.MenuItemViewHolder
    >() {
            
    class MenuItemViewHolder(
        itemLibraryListBinding: ItemLibraryListBinding
    ) : RecyclerView.ViewHolder(itemLibraryListBinding.root) {
        
        private val exerciseText = itemLibraryListBinding.text

        fun bind(item: LibraryDto) {
            when (item) {
                is LibraryDto.Category -> exerciseText.text = item.title
                is LibraryDto.SubCategory -> exerciseText.text = item.title
                is LibraryDto.Exercise -> {
                    exerciseText.text = item.title
                    exerciseText.setCompoundDrawables(null, null, null, null)
                }
            }
        }
    }

    override fun isForViewType(
        item: LibraryDto,
        items: MutableList<LibraryDto>,
        position: Int
    ): Boolean {
        return true
    }

    override fun onCreateViewHolder(parent: ViewGroup): MenuItemViewHolder {
        return MenuItemViewHolder(
            ItemLibraryListBinding.inflate(
                LayoutInflater.from(parent.context), parent, false
            )
        )
    }

    override fun onBindViewHolder(
        item: LibraryDto,
        holder: MenuItemViewHolder,
        payloads: MutableList<Any>
    ) {
        holder.bind(item)
    }
}
