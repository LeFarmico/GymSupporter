package com.lefarmico.core.utils

import android.view.animation.Animation
import android.view.animation.AnimationUtils
import androidx.annotation.AnimRes
import androidx.annotation.StringRes
import androidx.fragment.app.Fragment
import java.time.LocalDate
import java.time.LocalDateTime

object Utilities {

    fun getCurrentDate(): LocalDateTime = LocalDate.now().atStartOfDay()

    fun Fragment.getAnimation(@AnimRes id: Int): Animation =
        AnimationUtils.loadAnimation(requireContext(), id)

    fun Fragment.getStringResource(@StringRes id: Int): String =
        this.requireContext().getString(id)
}
