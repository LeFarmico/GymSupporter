package com.lefarmico.donetime.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.lefarmico.donetime.data.entities.currentExercise.ExerciseSetEntity
import com.lefarmico.donetime.databinding.ItemExerciseSetBinding

class CurrentSetAdapter : RecyclerView.Adapter<CurrentSetAdapter.SetViewHolder>() {

    var items = mutableListOf<ExerciseSetEntity>()
        set(value) {
            field = value
            notifyDataSetChanged()
        }

    class SetViewHolder(
        itemExerciseSetBinding: ItemExerciseSetBinding
    ) : RecyclerView.ViewHolder(itemExerciseSetBinding.root) {

        private val setNumber: TextView = itemExerciseSetBinding.setNumber
        private val weights: TextView = itemExerciseSetBinding.weights
        private var reps = itemExerciseSetBinding.reps

        fun bind(item: ExerciseSetEntity) {
            setNumber.text = "${item.setNumber}. Set"
            weights.text = "${item.weights} Kg"
            reps.text = "${item.reps} Reps"
        }
    }

    fun addItem(item: ExerciseSetEntity) {
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
