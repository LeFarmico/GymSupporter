package com.lefarmico.core.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.lefarmico.core.adapter.diffUtil.WorkoutRecordsDiffCallback
import com.lefarmico.core.databinding.ItemSocketExercisePreviewBinding
import com.lefarmico.core.entity.WorkoutRecordsViewData
import com.lefarmico.core.selector.SelectItemsHandler

class WorkoutRecordsAdapter :
    RecyclerView.Adapter<WorkoutRecordsAdapter.WorkoutNoteViewHolder>(),
    SelectItemsHandler.Callback<WorkoutRecordsViewData.WorkoutWithExercisesAndSets> {

    private var toggleButtonVisibility = View.GONE
    private val selectedItemsSet = mutableSetOf<WorkoutRecordsViewData.WorkoutWithExercisesAndSets>()

    lateinit var onEditButtonAction: (WorkoutRecordsViewData.Workout) -> Unit

    var items = listOf<WorkoutRecordsViewData.WorkoutWithExercisesAndSets>()
        set(value) {
            val oldField = field
            field = value
            val diffCallback = WorkoutRecordsDiffCallback(oldField, field)
            val diffResult = DiffUtil.calculateDiff(diffCallback)
            diffResult.dispatchUpdatesTo(this)
        }

    class WorkoutNoteViewHolder(
        itemSocketExercisePreviewBinding: ItemSocketExercisePreviewBinding
    ) : RecyclerView.ViewHolder(itemSocketExercisePreviewBinding.root) {

        private val date = itemSocketExercisePreviewBinding.date
        private val title = itemSocketExercisePreviewBinding.title

        val selectToggleButton = itemSocketExercisePreviewBinding.editButton
        val detailsButton = itemSocketExercisePreviewBinding.detailsButton

        fun bind(noteWorkout: WorkoutRecordsViewData.Workout) {
            val dateFormatted = noteWorkout.date
            date.text = dateFormatted
            title.text = noteWorkout.title
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): WorkoutNoteViewHolder {
        return WorkoutNoteViewHolder(
            ItemSocketExercisePreviewBinding.inflate(
                LayoutInflater.from(parent.context), parent, false
            )
        )
    }

    override fun onBindViewHolder(holder: WorkoutNoteViewHolder, position: Int) {
        holder.apply {
            bind(items[position].workout)
            detailsButton.setOnClickListener {
                onEditButtonAction(items[position].workout)
            }
            selectToggleButton.setOnCheckedChangeListener { _, isChecked ->
                // TODO вынести логику, но куда?
                when (isChecked) {
                    true -> selectedItemsSet.add(items[bindingAdapterPosition])
                    false -> selectedItemsSet.remove(items[bindingAdapterPosition])
                }
            }
        }
    }

    override fun onBindViewHolder(
        holder: WorkoutNoteViewHolder,
        position: Int,
        payloads: MutableList<Any>
    ) {
        if (payloads.isNotEmpty()) {
            when (payloads.last()) {
                SELECT_ALL -> holder.selectToggleButton.isChecked = true
                UNSELECT_ALL -> holder.selectToggleButton.isChecked = false
                EDIT_STATE -> {
                    holder.selectToggleButton.visibility = toggleButtonVisibility
                    if (toggleButtonVisibility == View.GONE) {
                        holder.selectToggleButton.isChecked = false
                    }
                }
            }
        } else {
            super.onBindViewHolder(holder, position, payloads)
        }
    }

    override fun getItemCount(): Int {
        return items.size
    }

    fun turnOnEditState() {
        if (toggleButtonVisibility != View.VISIBLE) {
            toggleButtonVisibility = View.VISIBLE
            notifyItemRangeChanged(0, items.size, EDIT_STATE)
        }
    }
    fun turnOffEditState() {
        if (toggleButtonVisibility != View.GONE) {
            toggleButtonVisibility = View.GONE
            notifyItemRangeChanged(0, items.size, EDIT_STATE)
        }
    }

    fun toggleSelectAll() {
        selectedItemsSet.clear()
        selectedItemsSet.addAll(items)
        notifyItemRangeChanged(0, items.size, SELECT_ALL)
    }

    fun toggleUnselectAll() {
        selectedItemsSet.clear()
        notifyItemRangeChanged(0, items.size, UNSELECT_ALL)
    }

    companion object {
        const val EDIT_STATE = "edit_state"
        const val SELECT_ALL = "select_all"
        const val UNSELECT_ALL = "unselect_all"
    }

    override fun getSelectedItems(): Set<WorkoutRecordsViewData.WorkoutWithExercisesAndSets> =
        selectedItemsSet
}
