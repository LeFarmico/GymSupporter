package com.lefarmico.donetime.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.lefarmico.donetime.data.entities.note.SetNote
import com.lefarmico.donetime.databinding.ItemNoteSetBinding

class SetNoteAdapter : RecyclerView.Adapter<SetNoteAdapter.SetViewHolder>() {

    class SetViewHolder(
        itemNoteSetBinding: ItemNoteSetBinding
    ) : RecyclerView.ViewHolder(itemNoteSetBinding.root) {

        private var reps = itemNoteSetBinding.reps
        private var setNumber = itemNoteSetBinding.setNumber
        private var wieght = itemNoteSetBinding.weight
        
        fun bind(setNote: SetNote) {
            reps.text = "Reps: ${setNote.reps} "
            wieght.text = "Weight: ${setNote.weight} "
        }
        fun bindSetNumber(number: Int) {
            setNumber.text = "$number. "
        }
    }
    var items = mutableListOf<SetNote>()
        set(value) {
            field = value
            notifyDataSetChanged()
        }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): SetViewHolder {
        return SetViewHolder(
            ItemNoteSetBinding.inflate(
                LayoutInflater.from(parent.context), parent, false
            )
        )
    }

    override fun onBindViewHolder(holder: SetViewHolder, position: Int) {
        holder.bind(items[position])
        holder.bindSetNumber(position + 1)
    }

    override fun getItemCount(): Int {
        return items.size
    }
}
