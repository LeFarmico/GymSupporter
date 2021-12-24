package com.lefarmico.core.customView.menuView

import android.content.Context
import android.util.AttributeSet
import android.view.LayoutInflater
import android.widget.LinearLayout
import com.lefarmico.core.R
import com.lefarmico.core.databinding.MenuItemBinding

class MenuItemView @JvmOverloads constructor(
    context: Context,
    attributeSet: AttributeSet? = null,
    defStyleAttr: Int = 0
) : LinearLayout(context, attributeSet, defStyleAttr) {

    val binding = MenuItemBinding.inflate(LayoutInflater.from(context), this, true)

    var title = binding.text

    fun drawableEnd(isShown: Boolean) {
        val drawable = if (isShown) {
            context.getDrawable(R.drawable.ic_baseline_arrow_forward_ios_24)
        } else {
            null
        }
        title.setCompoundDrawables(
            null,
            null,
            drawable,
            null
        )
    }
}
