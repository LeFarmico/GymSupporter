package com.lefarmico.lerecycle
import androidx.recyclerview.widget.RecyclerView

fun <T : RecyclerView.ViewHolder> T.listen(event: (position: Int, type: Int) -> Unit): T {
    itemView.setOnClickListener {
        event.invoke(bindingAdapterPosition, itemViewType)
    }
    return this
}
