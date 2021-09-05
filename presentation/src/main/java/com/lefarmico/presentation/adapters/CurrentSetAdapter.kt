package com.lefarmico.donetime.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.lefarmico.donetime.data.entities.currentExercise.ExerciseSet
import com.lefarmico.donetime.databinding.ItemExerciseSetBinding

class CurrentSetAdapter : RecyclerView.Adapter<CurrentSetAdapter.SetViewHolder>() {

    var items = mutableListOf<ExerciseSet>()
        set(value) {
            field = value
            notifyDataSetChanged()
        }

    class SetViewHolder(
        itemExerciseSetBinding: ItemExerciseSetBinding
    ) : RecyclerView.ViewHolder(itemExerciseSetBinding.root) {

        private val setNumber: TextView = itemExerciseSetBinding.setNumber
        private val weights: TextView = itemExerciseSetBinding.weight
        private var reps = itemExerciseSetBinding.reps

        fun bind(item: ExerciseSet) {
            setNumber.text = item.setNumber.toString()
            weights.text = item.weights.toString()
            reps.text = item.reps.toString()
        }
    }

    fun addItem(item: ExerciseSet) {
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
//        holder.itemView.setOnClickListener {
//            Log.d("!!!!!!!", "onClick")
//        }
    }

    override fun getItemCount(): Int {
        return items.size
    }
}
