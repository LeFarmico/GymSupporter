package com.lefarmico.data.preference

import android.content.Context
import com.lefarmico.domain.preferences.RemindPreferenceHelper
import javax.inject.Inject

class RemindPreferenceHelperImpl @Inject constructor(
    context: Context
) : RemindPreferenceHelper {

    private val preference = context.getSharedPreferences(REMIND_TIME_FILE, 0)

    override fun getBeforeRemindTimeRes(): Int {
        return preference.getInt(REMIND_TIME_PREFERENCE_KEY, 0)
    }

    override fun setBeforeRemindTimeRes(resId: Int) {
        preference.edit().putInt(REMIND_TIME_PREFERENCE_KEY, resId).apply()
    }

    companion object {
        const val REMIND_TIME_PREFERENCE_KEY = "RemindTimePreferenceKey"
        const val REMIND_TIME_FILE = "RemindTimeFile"
    }
}
