package com.lefarmico.core.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.lefarmico.core.adapter.LibraryItemAdapter.MenuItemViewHolder
import com.lefarmico.core.adapter.diffUtil.ExerciseLibraryDiffCallback
import com.lefarmico.core.databinding.MenuItemBinding
import com.lefarmico.core.entity.LibraryViewData

class LibraryItemAdapter : RecyclerView.Adapter<MenuItemViewHolder>() {

    lateinit var onClick: (LibraryViewData) -> Unit

    var items = listOf<LibraryViewData>()
        set(value) {
            val oldItems = field
            field = value
            val diffCallback = ExerciseLibraryDiffCallback(oldItems, field)
            val diffResult = DiffUtil.calculateDiff(diffCallback)
            diffResult.dispatchUpdatesTo(this)
        }

    class MenuItemViewHolder(
        itemLibraryListBinding: MenuItemBinding
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

    override fun onBindViewHolder(holder: MenuItemViewHolder, position: Int) {
        holder.bind(items[position])
        holder.itemView.setOnClickListener {
            onClick(items[position])
        }
    }

    override fun onBindViewHolder(
        holder: MenuItemViewHolder,
        position: Int,
        payloads: MutableList<Any>
    ) {
        super.onBindViewHolder(holder, position, payloads)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MenuItemViewHolder {
        return MenuItemViewHolder(
            MenuItemBinding.inflate(
                LayoutInflater.from(parent.context), parent, false
            )
        )
    }

    override fun getItemCount(): Int = items.size
}
