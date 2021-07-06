package com.lefarmico.lerecycle

import android.util.Log
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView

class PaginationOnScrollListener(
    private val layoutManager: RecyclerView.LayoutManager,
    private val pagination: () -> Unit
) : RecyclerView.OnScrollListener() {

    var loading = true

    override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
        super.onScrolled(recyclerView, dx, dy)
        if (dx > 0) {
            val visibleItemCount = layoutManager.childCount
            val totalItemCount = layoutManager.itemCount
            val firstVisibleItemPos = (layoutManager as LinearLayoutManager)
                .findFirstVisibleItemPosition()

            if (loading) {
                if ((visibleItemCount + firstVisibleItemPos) >= totalItemCount) {
                    loading = false
                    Log.v(
                        "Pagination",
                        "Last item: ${(visibleItemCount + firstVisibleItemPos)}, totalItems: $totalItemCount"
                    )
                    // Do pagination
                    pagination()
                    loading = true
                }
            }
        }
    }
}
