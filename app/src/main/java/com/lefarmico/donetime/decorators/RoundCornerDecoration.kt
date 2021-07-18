package com.lefarmico.donetime.decorators

import android.content.Context
import android.graphics.Canvas
import android.graphics.Rect
import android.graphics.RectF
import android.view.View
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.RecyclerView

class RoundCornerDecoration(context: Context) : RecyclerView.ItemDecoration() {

    val radius = 5.0f
    val defaultRect = RectF(Float.MAX_VALUE, Float.MAX_VALUE, 0f ,0f)

    override fun getItemOffsets(
        outRect: Rect,
        view: View,
        parent: RecyclerView,
        state: RecyclerView.State
    ) {
        super.getItemOffsets(outRect, view, parent, state)
    }

    override fun onDraw(c: Canvas, parent: RecyclerView, state: RecyclerView.State) {

        super.onDraw(c, parent, state)
    }

    fun clipCorners() {

    }

    private fun getRect(parent: RecyclerView) {

    }
}
