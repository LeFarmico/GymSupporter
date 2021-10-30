package com.lefarmico.core.adapter.delegates.exerciseLibraryDelegates

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.hannesdorfmann.adapterdelegates4.AbsListItemAdapterDelegate
import com.lefarmico.core.databinding.ItemLibraryListBinding
import com.lefarmico.core.entity.LibraryViewData

class LibraryItemDelegate : AbsListItemAdapterDelegate<
    LibraryViewData,
    LibraryViewData,
    LibraryItemDelegate.MenuItemViewHolder
    >() {

    class MenuItemViewHolder(
        itemLibraryListBinding: ItemLibraryListBinding
    ) : RecyclerView.ViewHolder(itemLibraryListBinding.root) {

        private val exerciseText = itemLibraryListBinding.text

        fun bind(item: LibraryViewData) {
            when (item) {
                is LibraryViewData.Category -> exerciseText.text = item.title
                is LibraryViewData.SubCategory -> exerciseText.text = item.title
                is LibraryViewData.Exercise -> {
                    exerciseText.text = item.title
                    exerciseText.setCompoundDrawables(null, null, null, null)
                }
            }
        }
    }

    override fun isForViewType(
        item: LibraryViewData,
        items: MutableList<LibraryViewData>,
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
        item: LibraryViewData,
        holder: MenuItemViewHolder,
        payloads: MutableList<Any>
    ) {
        holder.bind(item)
    }
}
