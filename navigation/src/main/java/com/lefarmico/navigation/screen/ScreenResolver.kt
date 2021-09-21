package com.lefarmico.navigation.screen

import android.os.Parcelable
import androidx.navigation.NavController

interface ScreenResolver {

    fun navigate(
        navController: NavController? = null,
        screen: Screen,
        data: Parcelable? = null,
        sharedElements: Map<Any, String>? = null
    )
}
