package com.lefarmico.core.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.lefarmico.core.databinding.MenuItemBinding

class MenuAdapter : RecyclerView.Adapter<MenuAdapter.MenuViewHolder>() {

    var onClick: (Int) -> Unit = {}
    var items = listOf<String>()
        set(value) {
            field = value
            notifyItemRangeChanged(0, value.size)
        }

    class MenuViewHolder(
        item: MenuItemBinding
    ) : RecyclerView.ViewHolder(item.root) {

        val title = item.text

        fun bind(text: String) {
            title.text = text
            title.setCompoundDrawables(null, null, null, null)
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MenuViewHolder {
        return MenuViewHolder(
            MenuItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        )
    }

    override fun onBindViewHolder(holder: MenuViewHolder, position: Int) {
        holder.bind(items[position])
        holder.title.setOnClickListener { onClick(position) }
    }

    override fun getItemCount(): Int {
        return items.size
    }
}
