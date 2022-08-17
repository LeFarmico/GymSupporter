package com.lefarmico.core.adapter

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.RecyclerView
import com.lefarmico.core.adapter.diffUtil.CurrentExerciseDiffCallback
import com.lefarmico.core.databinding.ItemExerciseBinding
import com.lefarmico.core.entity.CurrentWorkoutViewData
import com.lefarmico.core.selector.SelectItemsHandler

class CurrentExerciseAdapter(
    var plusButtonCallback: (Int) -> Unit = {},
    var minusButtonCallback: (Int) -> Unit = {},
    var infoButtonCallback: (Int) -> Unit = {},
    var onSetClick: (CurrentWorkoutViewData.Set) -> Unit = {}
) :
    RecyclerView.Adapter<CurrentExerciseAdapter.ExerciseViewHolder>(),
    SelectItemsHandler.Callback<CurrentWorkoutViewData.ExerciseWithSets> {

    private var toggleButtonVisibility = View.GONE
    private val selectedItemsSet = mutableSetOf<CurrentWorkoutViewData.ExerciseWithSets>()

    var items = listOf<CurrentWorkoutViewData.ExerciseWithSets>()
        set(value) {
            val oldField = field
            field = value
            val diffCallback = CurrentExerciseDiffCallback(oldField, field)
            val diffResult = DiffUtil.calculateDiff(diffCallback)
            diffResult.dispatchUpdatesTo(this)
        }

    inner class ExerciseViewHolder(
        itemExerciseBinding: ItemExerciseBinding
    ) : RecyclerView.ViewHolder(itemExerciseBinding.root) {

        val adapter = CurrentSetAdapter()

        private val recycler = itemExerciseBinding.exerciseItem
        private val exerciseTitle = itemExerciseBinding.exerciseName

        val selectToggleButton = itemExerciseBinding.editButton
        val plusButton = itemExerciseBinding.buttons.plusButton
        val minusButton = itemExerciseBinding.buttons.minusButton
        val infoButton = itemExerciseBinding.buttons.infoButton

        private val decorator = DividerItemDecoration(recycler.context, DividerItemDecoration.VERTICAL)

        fun onSetClickCallback(set: (CurrentWorkoutViewData.Set) -> Unit) {
            adapter.onItemClick = set
        }
        fun bindAdapter() {
            recycler.adapter = adapter
            recycler.addItemDecoration(decorator, 0)
        }
        fun bind(title: String) {
            exerciseTitle.text = title
        }

        fun bindItems(setList: List<CurrentWorkoutViewData.Set>) {
            adapter.items = setList
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ExerciseViewHolder {
        return ExerciseViewHolder(
            ItemExerciseBinding.inflate(
                LayoutInflater.from(parent.context), parent, false
            )
        )
    }

    override fun onBindViewHolder(
        holder: ExerciseViewHolder,
        position: Int,
        payloads: MutableList<Any>
    ) {
        if (payloads.isNotEmpty()) {
            when (payloads.last()) {
                is Bundle -> {
                    val bundle = payloads[0] as Bundle
                    for (key in bundle.keySet()) {
                        if (key.equals(SET_LIST_UPDATE)) {
                            val setList = bundle.getParcelableArrayList<CurrentWorkoutViewData.Set>(key)
                            holder.bindItems(setList!!.toList())
                        }
                    }
                }
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

    override fun onBindViewHolder(holder: ExerciseViewHolder, position: Int) {
        val title = items[position].exercise.title
        holder.apply {
            plusButton.setOnClickListener {
                val id = items[bindingAdapterPosition].exercise.id
                plusButtonCallback(id)
            }
            minusButton.setOnClickListener {
                minusButtonCallback(items[bindingAdapterPosition].exercise.id)
            }
            infoButton.setOnClickListener {
                infoButtonCallback(items[bindingAdapterPosition].exercise.libraryId)
            }
            onSetClickCallback {
                onSetClick(it)
            }
            selectToggleButton.setOnCheckedChangeListener { _, isChecked ->
                when (isChecked) {
                    true -> selectedItemsSet.add(items[bindingAdapterPosition])
                    false -> selectedItemsSet.remove(items[bindingAdapterPosition])
                }
            }
        }
        holder.bindItems(items[position].setList.toMutableList())
        holder.bindAdapter()
        holder.bind(title)
    }

    override fun getItemCount(): Int {
        return items.size
    }

    fun turnOnEditState() {
        if (toggleButtonVisibility != View.VISIBLE) {
            toggleButtonVisibility = View.VISIBLE
            notifyItemRangeChanged(0, items.size, WorkoutRecordsAdapter.EDIT_STATE)
        }
    }
    fun turnOffEditState() {
        if (toggleButtonVisibility != View.GONE) {
            toggleButtonVisibility = View.GONE
            notifyItemRangeChanged(0, items.size, WorkoutRecordsAdapter.EDIT_STATE)
        }
    }

    override fun getSelectedItems(): Set<CurrentWorkoutViewData.ExerciseWithSets> = selectedItemsSet

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
        const val SET_LIST_UPDATE = "set_list"
        const val EDIT_STATE = "edit_state"
        const val SELECT_ALL = "select_all"
        const val UNSELECT_ALL = "unselect_all"
    }
}
