package com.lefarmico.core.adapter.diffUtil

import androidx.recyclerview.widget.DiffUtil
import com.lefarmico.core.entity.LibraryViewData

class ExerciseLibraryDiffCallback(
    private val oldList: List<LibraryViewData>,
    private val newList: List<LibraryViewData>
) : DiffUtil.Callback() {
    override fun getOldListSize(): Int {
        return oldList.size
    }

    override fun getNewListSize(): Int {
        return newList.size
    }

    override fun areItemsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
        when (oldList[oldItemPosition]) {
            is LibraryViewData.Exercise -> {
                return if (newList[newItemPosition] is LibraryViewData.Exercise) {
                    val oldItem = oldList[oldItemPosition] as LibraryViewData.Exercise
                    val newItem = newList[newItemPosition] as LibraryViewData.Exercise
                    oldItem.hashCode() == newItem.hashCode()
                } else {
                    false
                }
            }
            is LibraryViewData.Category -> {
                return if (newList[newItemPosition] is LibraryViewData.Category) {
                    val oldItem = oldList[oldItemPosition] as LibraryViewData.Category
                    val newItem = newList[newItemPosition] as LibraryViewData.Category
                    oldItem.hashCode() == newItem.hashCode()
                } else {
                    false
                }
            }
            is LibraryViewData.SubCategory -> {
                return if (newList[newItemPosition] is LibraryViewData.SubCategory) {
                    val oldItem = oldList[oldItemPosition] as LibraryViewData.SubCategory
                    val newItem = newList[newItemPosition] as LibraryViewData.SubCategory
                    oldItem.hashCode() == newItem.hashCode()
                } else {
                    false
                }
            }
        }
    }

    override fun areContentsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
        when (oldList[oldItemPosition]) {
            is LibraryViewData.Exercise -> {
                return if (newList[newItemPosition] is LibraryViewData.Exercise) {
                    val oldItem = oldList[oldItemPosition] as LibraryViewData.Exercise
                    val newItem = newList[newItemPosition] as LibraryViewData.Exercise
                    oldItem == newItem
                } else {
                    false
                }
            }
            is LibraryViewData.SubCategory -> {
                return if (newList[newItemPosition] is LibraryViewData.SubCategory) {
                    val oldItem = oldList[oldItemPosition] as LibraryViewData.SubCategory
                    val newItem = newList[newItemPosition] as LibraryViewData.SubCategory
                    oldItem == newItem
                } else {
                    false
                }
            }
            is LibraryViewData.Category -> {
                return if (newList[newItemPosition] is LibraryViewData.Category) {
                    val oldItem = oldList[oldItemPosition] as LibraryViewData.Category
                    val newItem = newList[newItemPosition] as LibraryViewData.Category
                    oldItem == newItem
                } else {
                    false
                }
            }
        }
    }
}
