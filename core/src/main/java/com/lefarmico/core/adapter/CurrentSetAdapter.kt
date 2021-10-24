package com.lefarmico.core.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.TextView
import android.widget.Toast
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.lefarmico.core.adapter.diffUtil.CurrentSetDiffCallback
import com.lefarmico.core.databinding.ItemExerciseSetBinding
import com.lefarmico.domain.entity.CurrentWorkoutDto

class CurrentSetAdapter : RecyclerView.Adapter<CurrentSetAdapter.SetViewHolder>() {

    var items = mutableListOf<CurrentWorkoutDto.Set>()
        set(value) {
            val oldField = field
            field = value
            val diffCallback = CurrentSetDiffCallback(oldField, field)
            val diffResult = DiffUtil.calculateDiff(diffCallback)
            diffResult.dispatchUpdatesTo(this)
        }

    class SetViewHolder(
        itemExerciseSetBinding: ItemExerciseSetBinding
    ) : RecyclerView.ViewHolder(itemExerciseSetBinding.root) {

        private val setNumber: TextView = itemExerciseSetBinding.setNumber
        private val weights: TextView = itemExerciseSetBinding.weight
        private var reps = itemExerciseSetBinding.reps

        private val layout = itemExerciseSetBinding.root

        fun bind(item: CurrentWorkoutDto.Set) {
            setNumber.text = item.setNumber.toString()
            weights.text = item.weight.toString()
            reps.text = item.reps.toString()
            layout.setOnClickListener {
                Toast.makeText(it.context, "Push", Toast.LENGTH_SHORT).show()
            }
        }
    }

    fun addItem(item: CurrentWorkoutDto.Set) {
        items.add(item)
        notifyItemInserted(itemCount + 1)
    }
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): SetViewHolder {
        return SetViewHolder(
            ItemExerciseSetBinding.inflate(
                LayoutInflater.from(parent.context), parent, false
            )
        )
    }

    override fun onBindViewHolder(holder: SetViewHolder, position: Int) {
        holder.bind(items[position])
        holder.itemView.isClickable = false
        holder.itemView.isFocusable = false
    }

    override fun getItemCount(): Int {
        return items.size
    }
}
