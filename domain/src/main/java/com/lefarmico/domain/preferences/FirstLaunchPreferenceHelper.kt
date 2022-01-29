package com.lefarmico.domain.preferences

interface FirstLaunchPreferenceHelper {
    fun getState(): Boolean

    fun setState(isFirst: Boolean)
}
