package com.lefarmico.core.adapter.decorators

import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.RecyclerView
import com.lefarmico.core.adapter.CurrentSetAdapter
import java.util.*

class CurrentSetTouchHelperCallback(val adapter: CurrentSetAdapter) : ItemTouchHelper.Callback() {

    override fun getMovementFlags(
        recyclerView: RecyclerView,
        viewHolder: RecyclerView.ViewHolder
    ): Int {
        val dragFlags = ItemTouchHelper.UP or ItemTouchHelper.DOWN
        val swipeFlags = ItemTouchHelper.START or ItemTouchHelper.DOWN
        return makeMovementFlags(dragFlags, swipeFlags)
    }

    override fun onMove(
        recyclerView: RecyclerView,
        viewHolder: RecyclerView.ViewHolder,
        target: RecyclerView.ViewHolder
    ): Boolean {
        val items = adapter.items
        val fromPos = viewHolder.absoluteAdapterPosition
        val toPos = target.absoluteAdapterPosition
        if (fromPos < toPos) {
            for (i in fromPos until toPos) {
                Collections.swap(items, i, i + 1)
            }
        } else {
            for (i in fromPos downTo toPos + 1) {
                Collections.swap(items, i, i - 1)
            }
        }
        return true
    }

    override fun onSwiped(viewHolder: RecyclerView.ViewHolder, direction: Int) {
        TODO("Not yet implemented")
    }
}
