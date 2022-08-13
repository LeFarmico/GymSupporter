package com.lefarmico.core.decorator

import android.graphics.Rect
import android.view.View
import androidx.annotation.IntDef
import androidx.recyclerview.widget.RecyclerView
import com.lefarmico.core.extensions.dp

class ItemSpaceDecoration(
    private val spacingDp: Int,
    @SpaceDecorOrientation private val orientation: Int
) : RecyclerView.ItemDecoration() {

    override fun getItemOffsets(
        outRect: Rect,
        view: View,
        parent: RecyclerView,
        state: RecyclerView.State
    ) {
        when (orientation) {
            VERTICAL -> {
                if (parent.getChildAdapterPosition(view) != parent.adapter!!.itemCount - 1)
                    outRect.bottom = spacingDp.dp
            }
            HORIZONTAL -> {
                if (parent.getChildAdapterPosition(view) != parent.adapter!!.itemCount - 1)
                    outRect.right = spacingDp.dp
            }
        }
    }

    companion object {

        @IntDef(VERTICAL, HORIZONTAL)
        @Retention(AnnotationRetention.SOURCE)
        annotation class SpaceDecorOrientation

        const val VERTICAL = 0
        const val HORIZONTAL = 1
    }
}
