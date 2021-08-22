package com.lefarmico.donetime.adapters.delegates.exerciseLibraryDelegates

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.hannesdorfmann.adapterdelegates4.AbsListItemAdapterDelegate
import com.lefarmico.donetime.data.entities.library.ILibraryItem
import com.lefarmico.donetime.data.entities.library.LibraryCategory
import com.lefarmico.donetime.data.entities.library.LibraryExercise
import com.lefarmico.donetime.data.entities.library.LibrarySubCategory
import com.lefarmico.donetime.databinding.ItemLibraryListBinding

class ExerciseLibraryCategoryItem : AbsListItemAdapterDelegate<
    ILibraryItem,
    ILibraryItem,
    ExerciseLibraryCategoryItem.MenuItemViewHolder
    >() {
            
    class MenuItemViewHolder(
        itemLibraryListBinding: ItemLibraryListBinding
    ) : RecyclerView.ViewHolder(itemLibraryListBinding.root) {
        
        private val exerciseText = itemLibraryListBinding.text

        fun bind(item: ILibraryItem) {
            when (item) {
                is LibraryCategory -> {
                    exerciseText.text = item.title
                }
                is LibrarySubCategory -> {
                    exerciseText.text = item.title
                }
                is LibraryExercise -> {
                    exerciseText.text = item.title
                    exerciseText.setCompoundDrawables(null, null, null, null)
                }
            }
        }
    }

    override fun isForViewType(
        item: ILibraryItem,
        items: MutableList<ILibraryItem>,
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
        item: ILibraryItem,
        holder: MenuItemViewHolder,
        payloads: MutableList<Any>
    ) {
        holder.bind(item)
    }
}
