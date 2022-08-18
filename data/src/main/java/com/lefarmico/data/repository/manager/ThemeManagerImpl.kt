package com.lefarmico.data.repository.manager

import android.util.Log
import com.lefarmico.domain.preferences.ThemeSettingsPreferenceHelper
import com.lefarmico.domain.repository.manager.ThemeManager
import io.reactivex.rxjava3.core.Single
import javax.inject.Inject

class ThemeManagerImpl @Inject constructor(
    private val helper: ThemeSettingsPreferenceHelper
) : ThemeManager {

    override fun getCurrentTheme(): Single<Int> {
        return Single.create {
            val themeId = helper.getCurrentThemeRes()
            it.onSuccess(themeId)
        }
    }

    override fun setCurrentTheme(themeId: Int) {
        helper.setCurrentThemeRes(themeId)
    }
}
