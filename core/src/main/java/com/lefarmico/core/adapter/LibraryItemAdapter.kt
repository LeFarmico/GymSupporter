package com.lefarmico.core.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.lefarmico.core.adapter.LibraryItemAdapter.MenuItemViewHolder
import com.lefarmico.core.adapter.diffUtil.ExerciseLibraryDiffCallback
import com.lefarmico.core.databinding.MenuItemBinding
import com.lefarmico.core.entity.LibraryViewData
import com.lefarmico.core.selector.SelectItemsHandler

class LibraryItemAdapter :
    RecyclerView.Adapter<MenuItemViewHolder>(),
    SelectItemsHandler.Callback<LibraryViewData> {

    private var toggleButtonVisibility = View.GONE
    private var isEditState = false
    private val selectedItemsSet = mutableSetOf<LibraryViewData>()

    lateinit var onClick: (LibraryViewData) -> Unit

    var items = listOf<LibraryViewData>()
        set(value) {
            toggleButtonVisibility = View.GONE
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

        val selectToggleButton = itemLibraryListBinding.editButton

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
            if (!isEditState) onClick(items[position])
        }
        holder.selectToggleButton.setOnCheckedChangeListener { _, isChecked ->
            when (isChecked) {
                true -> selectedItemsSet.add(items[position])
                false -> selectedItemsSet.remove(items[position])
            }
        }
    }

    override fun onBindViewHolder(
        holder: MenuItemViewHolder,
        position: Int,
        payloads: MutableList<Any>
    ) {
        if (payloads.isNotEmpty()) {
            when (payloads.last()) {
                EDIT_STATE -> {
                    holder.selectToggleButton.visibility = toggleButtonVisibility
                    if (toggleButtonVisibility == View.GONE) {
                        holder.selectToggleButton.isChecked = false
                    }
                }
                SELECT_ALL -> holder.selectToggleButton.isChecked = true
                UNSELECT_ALL -> holder.selectToggleButton.isChecked = false
            }
        } else {
            super.onBindViewHolder(holder, position, payloads)
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MenuItemViewHolder {
        return MenuItemViewHolder(
            MenuItemBinding.inflate(
                LayoutInflater.from(parent.context), parent, false
            )
        )
    }

    override fun getItemCount(): Int = items.size

    override fun getSelectedItems(): Set<LibraryViewData> = selectedItemsSet

    fun turnOnEditState() {
        isEditState = true
        if (toggleButtonVisibility != View.VISIBLE) {
            toggleButtonVisibility = View.VISIBLE
            notifyItemRangeChanged(0, items.size, WorkoutRecordsAdapter.EDIT_STATE)
        }
    }
    fun turnOffEditState() {
        isEditState = false
        if (toggleButtonVisibility != View.GONE) {
            toggleButtonVisibility = View.GONE
            notifyItemRangeChanged(0, items.size, WorkoutRecordsAdapter.EDIT_STATE)
        }
    }

    fun toggleSelectAll() {
        selectedItemsSet.clear()
        selectedItemsSet.addAll(items)
        notifyItemRangeChanged(0, items.size, WorkoutRecordsAdapter.SELECT_ALL)
    }

    // TODO реализовать
    fun toggleUnselectAll() {
        selectedItemsSet.clear()
        notifyItemRangeChanged(0, items.size, WorkoutRecordsAdapter.UNSELECT_ALL)
    }

    companion object {
        const val EDIT_STATE = "edit_state"
        const val SELECT_ALL = "select_all"
        const val UNSELECT_ALL = "unselect_all"
    }
}
