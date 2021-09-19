package com.lefarmico.core.adapter.diffUtil

import androidx.recyclerview.widget.DiffUtil
import com.lefarmico.domain.entity.LibraryDto

class ExerciseLibraryDiffCallback(
    private val oldList: List<LibraryDto>,
    private val newList: List<LibraryDto>
) : DiffUtil.Callback() {
    override fun getOldListSize(): Int {
        return oldList.size
    }

    override fun getNewListSize(): Int {
        return newList.size
    }

    override fun areItemsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
        when (oldList[oldItemPosition]) {
            is LibraryDto.Exercise -> {
                return if (newList[newItemPosition] is LibraryDto.Exercise) {
                    val oldItem = oldList[oldItemPosition] as LibraryDto.Exercise
                    val newItem = newList[newItemPosition] as LibraryDto.Exercise
                    oldItem.hashCode() == newItem.hashCode()
                } else {
                    false
                }
            }
            is LibraryDto.Category -> {
                return if (newList[newItemPosition] is LibraryDto.Category) {
                    val oldItem = oldList[oldItemPosition] as LibraryDto.Category
                    val newItem = newList[newItemPosition] as LibraryDto.Category
                    oldItem.hashCode() == newItem.hashCode()
                } else {
                    false
                }
            }
            is LibraryDto.SubCategory -> {
                return if (newList[newItemPosition] is LibraryDto.SubCategory) {
                    val oldItem = oldList[oldItemPosition] as LibraryDto.SubCategory
                    val newItem = newList[newItemPosition] as LibraryDto.SubCategory
                    oldItem.hashCode() == newItem.hashCode()
                } else {
                    false
                }
            }
        }
    }

    override fun areContentsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
        when (oldList[oldItemPosition]) {
            is LibraryDto.Exercise -> {
                return if (newList[newItemPosition] is LibraryDto.Exercise) {
                    val oldItem = oldList[oldItemPosition] as LibraryDto.Exercise
                    val newItem = newList[newItemPosition] as LibraryDto.Exercise
                    oldItem == newItem
                } else {
                    false
                }
            }
            is LibraryDto.SubCategory -> {
                return if (newList[newItemPosition] is LibraryDto.SubCategory) {
                    val oldItem = oldList[oldItemPosition] as LibraryDto.SubCategory
                    val newItem = newList[newItemPosition] as LibraryDto.SubCategory
                    oldItem == newItem
                } else {
                    false
                }
            }
            is LibraryDto.Category -> {
                return if (newList[newItemPosition] is LibraryDto.Category) {
                    val oldItem = oldList[oldItemPosition] as LibraryDto.Category
                    val newItem = newList[newItemPosition] as LibraryDto.Category
                    oldItem == newItem
                } else {
                    false
                }
            }
        }
    }
}
