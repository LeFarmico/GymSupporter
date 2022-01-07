package com.lefarmico.core.customView.menuView

import android.content.Context
import android.util.AttributeSet
import android.view.LayoutInflater
import android.widget.LinearLayout
import com.lefarmico.core.databinding.MenuItemSummaryBinding

class MenuItemSummaryView @JvmOverloads constructor(
    context: Context,
    attributeSet: AttributeSet? = null,
    defStyleAttr: Int = 0
) : LinearLayout(context, attributeSet, defStyleAttr) {

    val binding = MenuItemSummaryBinding.inflate(LayoutInflater.from(context), this, true)

    val titleTextView = binding.title

    val summaryTextView = binding.summary
}
