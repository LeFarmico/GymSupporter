package com.lefarmico.data.preference

import android.content.Context
import com.lefarmico.domain.preferences.FirstLaunchPreferenceHelper
import javax.inject.Inject

class FirstLaunchPreferenceHelperImpl @Inject constructor(
    context: Context
) : FirstLaunchPreferenceHelper {

    private val preference = context.getSharedPreferences(FIRST_LAUNCH_PREFERENCE_FILE, Context.MODE_PRIVATE)

    override fun getState(): Boolean {
        return preference.getBoolean(FIRST_LAUNCH_PREFERENCE_KEY, true)
    }

    override fun setState(isFirst: Boolean) {
        preference.edit().putBoolean(FIRST_LAUNCH_PREFERENCE_KEY, isFirst).apply()
    }

    companion object {
        const val FIRST_LAUNCH_PREFERENCE_KEY = "FIRST_LAUNCH_PREFERENCE_KEY"
        const val FIRST_LAUNCH_PREFERENCE_FILE = "FIRST_LAUNCH_PREFERENCE_FILE"
    }
}
