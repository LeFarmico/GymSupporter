package com.lefarmico.navigation.params

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class SnackBarParams(
    val message: String,
    val listener: (() -> Unit)? = null
) : Parcelable
