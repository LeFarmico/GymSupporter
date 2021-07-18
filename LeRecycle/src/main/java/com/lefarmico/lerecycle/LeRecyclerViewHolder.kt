package com.lefarmico.lerecycle

import android.view.View
import androidx.annotation.NonNull
import androidx.recyclerview.widget.RecyclerView

abstract class LeRecyclerViewHolder<T : ItemType>(
    @NonNull val itemView: View
) : RecyclerView.ViewHolder(itemView) {
    abstract fun bind(item: T, position: Int, itemCount: Int)
}
