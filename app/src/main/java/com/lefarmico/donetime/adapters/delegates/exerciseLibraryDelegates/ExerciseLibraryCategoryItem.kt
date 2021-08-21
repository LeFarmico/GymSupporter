package com.lefarmico.donetime.adapters.delegates.exerciseLibraryDelegates

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.hannesdorfmann.adapterdelegates4.AbsListItemAdapterDelegate
import com.lefarmico.donetime.data.entities.library.ItemLibraryCategory
import com.lefarmico.donetime.data.entities.library.ItemLibraryExercise
import com.lefarmico.donetime.data.entities.library.ItemLibrarySubCategory
import com.lefarmico.donetime.data.models.IExerciseLibraryItem
import com.lefarmico.donetime.databinding.ItemExerciseListBinding

class ExerciseLibraryCategoryItem : AbsListItemAdapterDelegate<
    IExerciseLibraryItem,
    IExerciseLibraryItem,
    ExerciseLibraryCategoryItem.MenuItemViewHolder
    >() {
            
    class MenuItemViewHolder(
        itemExerciseListBinding: ItemExerciseListBinding
    ) : RecyclerView.ViewHolder(itemExerciseListBinding.root) {
        
        private val exerciseText = itemExerciseListBinding.text

        fun bind(item: IExerciseLibraryItem) {
            when (item) {
                is ItemLibraryCategory -> {
                    exerciseText.text = item.title
                }
                is ItemLibrarySubCategory -> {
                    exerciseText.text = item.title
                }
                is ItemLibraryExercise -> {
                    exerciseText.text = item.title
                    exerciseText.setCompoundDrawables(null, null, null, null)
                }
            }
        }
    }

    override fun isForViewType(
        item: IExerciseLibraryItem,
        items: MutableList<IExerciseLibraryItem>,
        position: Int
    ): Boolean {
        return item is IExerciseLibraryItem
    }

    override fun onCreateViewHolder(parent: ViewGroup): MenuItemViewHolder {
        return MenuItemViewHolder(
            ItemExerciseListBinding.inflate(
                LayoutInflater.from(parent.context), parent, false
            )
        )
    }

    override fun onBindViewHolder(
        item: IExerciseLibraryItem,
        holder: MenuItemViewHolder,
        payloads: MutableList<Any>
    ) {
        holder.bind(item)
    }
}
