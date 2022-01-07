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
//            notifyItemRangeChanged(0, items.size)
        }

    class MenuItemViewHolder(
        itemLibraryListBinding: MenuItemBinding
    ) : RecyclerView.ViewHolder(itemLibraryListBinding.root) {

        private val exerciseText = itemLibraryListBinding.text
        private val root = itemLibraryListBinding.root

//        fun bindBackground(@DrawableRes backgroundId: Int) {
//            val drawable = ContextCompat.getDrawable(root.context, backgroundId)
//            root.background = drawable
//        }
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

//        if (items.size == 1) {
//            holder.bindBackground(R.drawable.shape_menu_item_single)
//        } else if (items.size > 1) {
//            when (position) {
//                0 -> holder.bindBackground(R.drawable.shape_menu_item_top)
//                (items.size - 1) -> holder.bindBackground(R.drawable.shape_menu_item_bottom)
//                else -> holder.bindBackground(R.drawable.shape_menu_item_middle)
//            }
//        }
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
