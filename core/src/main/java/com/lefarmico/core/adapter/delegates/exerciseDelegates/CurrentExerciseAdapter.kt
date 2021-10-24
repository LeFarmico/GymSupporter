package com.lefarmico.core.adapter.delegates.exerciseDelegates

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.RecyclerView
import com.lefarmico.core.adapter.CurrentSetAdapter
import com.lefarmico.core.adapter.diffUtil.CurrentExerciseDiffCallback
import com.lefarmico.core.databinding.ItemExerciseBinding
import com.lefarmico.domain.entity.CurrentWorkoutDto

class CurrentExerciseAdapter : RecyclerView.Adapter<
    CurrentExerciseAdapter.ExerciseViewHolder
    >() {

    lateinit var plusButtonCallback: (Int) -> Unit
    lateinit var minusButtonCallback: (Int) -> Unit
    lateinit var infoButtonCallback: (Int) -> Unit
    var items = listOf<CurrentWorkoutDto.ExerciseWithSets>()
        set(value) {
            val oldField = field
            field = value
            val diffCallback = CurrentExerciseDiffCallback(oldField, field)
            val diffResult = DiffUtil.calculateDiff(diffCallback)
            diffResult.dispatchUpdatesTo(this)
        }
    class ExerciseViewHolder(
        itemExerciseBinding: ItemExerciseBinding
    ) : RecyclerView.ViewHolder(itemExerciseBinding.root) {

        private val recycler = itemExerciseBinding.exerciseItem
        private val exerciseTitle = itemExerciseBinding.exerciseTitle.exerciseName
        private val exerciseTag = itemExerciseBinding.exerciseTitle.tags

        val rootLayout = itemExerciseBinding.exerciseTitle.root

        val plusButton = itemExerciseBinding.buttons.plusButton
        val minusButton = itemExerciseBinding.buttons.minusButton
        val infoButton = itemExerciseBinding.buttons.infoButton

        private val decorator = DividerItemDecoration(recycler.context, DividerItemDecoration.VERTICAL)

        fun bindAdapter(adapter: CurrentSetAdapter) {
            recycler.adapter = adapter
            if (recycler.itemDecorationCount == 0) {
                recycler.addItemDecoration(decorator)
            }
        }
        fun bind(title: String, tag: String) {
            exerciseTitle.text = title
            exerciseTag.text = tag
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ExerciseViewHolder {
        return ExerciseViewHolder(
            ItemExerciseBinding.inflate(
                LayoutInflater.from(parent.context), parent, false
            )
        )
    }

    override fun onBindViewHolder(holder: ExerciseViewHolder, position: Int) {
        val title = items[position].exercise.title
        val adapter = CurrentSetAdapter()
        holder.apply {
            plusButton.setOnClickListener {
                plusButtonCallback(items[position].exercise.id)
            }
            minusButton.setOnClickListener {
                minusButtonCallback(items[position].exercise.id)
            }
            infoButton.setOnClickListener {
                infoButtonCallback(items[position].exercise.libraryId)
            }
        }
        adapter.items = items[position].setList.toMutableList()
        holder.bindAdapter(adapter)
        holder.bind(title, title)
    }

    override fun getItemCount(): Int {
        return items.size
    }
}
