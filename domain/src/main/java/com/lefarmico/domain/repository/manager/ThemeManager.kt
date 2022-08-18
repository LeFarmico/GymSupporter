package com.lefarmico.domain.repository.manager

import io.reactivex.rxjava3.core.Single

interface ThemeManager {

    fun getCurrentTheme(): Single<Int>

    fun setCurrentTheme(themeId: Int)
}
