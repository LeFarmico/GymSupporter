package com.lefarmico.lerecycle

import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView

open class LeRecyclerAdapter : RecyclerView.Adapter<LeRecyclerViewHolder<ItemType>>() {

    var items: MutableList<ItemType> = mutableListOf()
        set(value) {
            field = value
            notifyDataSetChanged()
        }
    private var onClickEvent: ((ItemType) -> Unit)? = null

    lateinit var itemTypes: Wrapper<IViewHolderFactory<ItemType>>

    open fun setItemTypes(types: Array<out IViewHolderFactory<ItemType>>) {
        itemTypes = Wrapper(types)
    }
    open fun setItemType(type: IViewHolderFactory<ItemType>) {
        itemTypes = Wrapper(arrayOf(type))
    }
    final override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): LeRecyclerViewHolder<ItemType> {
        return onCreateViewHolderWithListener(parent, viewType).listen { position, _ ->
            onClickEvent?.let { it(items[position]) }
        }
    }

    private fun onCreateViewHolderWithListener(
        parent: ViewGroup,
        viewType: Int
    ): LeRecyclerViewHolder<ItemType> {
        val factory = itemTypes.getType(viewType)
        return factory.createViewHolder(parent)
    }

    override fun onBindViewHolder(holder: LeRecyclerViewHolder<ItemType>, position: Int) {
        holder.bind(items[position], position, itemCount)
    }

    fun setOnClickEvent(event: (ItemType) -> Unit) {
        onClickEvent = event
    }

    override fun getItemCount(): Int {
        return items.size
    }

    override fun getItemViewType(position: Int): Int {
        val type = items[position].type
        return itemTypes.getViewTypeNumber(type)
    }
}
interface IViewHolderFactory<T : ItemType> {
    fun createViewHolder(parent: ViewGroup): LeRecyclerViewHolder<T>
}
interface ItemType {
    val type: IViewHolderFactory<ItemType>
}
