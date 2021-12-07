package com.lefarmico.core.customView

import android.content.Context
import android.util.AttributeSet
import androidx.annotation.IntDef
import androidx.appcompat.widget.AppCompatTextView
import com.lefarmico.core.R

class StyledTextView(
    context: Context,
    attributeSet: AttributeSet?,
    @TextStyle textStyle: Int
) : AppCompatTextView(context, attributeSet) {

    init {
        when (textStyle) {
            DEFAULT -> setTextAppearance(R.style.TextStyle)
            HEADLINE -> setTextAppearance(R.style.TextStyle_Headline)
            HEADLINE_BOLD -> setTextAppearance(R.style.TextStyle_HeadlineBold)
            CAPTION -> setTextAppearance(R.style.TextStyle_Caption)
            SUBHEAD -> setTextAppearance(R.style.TextStyle_SubHead)
        }
    }
    companion object {

        @IntDef(DEFAULT, HEADLINE, HEADLINE_BOLD, CAPTION, SUBHEAD)
        @Retention(AnnotationRetention.SOURCE)
        annotation class TextStyle

        const val DEFAULT = 0
        const val HEADLINE = 1
        const val HEADLINE_BOLD = 2
        const val CAPTION = 3
        const val SUBHEAD = 4
    }
}
