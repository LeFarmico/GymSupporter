package com.lefarmico.domain.preferences

interface FirstLaunchPreferenceHelper {

    fun onOnFirstLaunchListener(action: () -> Unit): Boolean

    fun reset(): Boolean
}
