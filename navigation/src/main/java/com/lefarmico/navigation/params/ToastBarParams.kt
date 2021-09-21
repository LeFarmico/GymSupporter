package com.lefarmico.navigation.params

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class ToastBarParams(
    val message: String
) : Parcelable
