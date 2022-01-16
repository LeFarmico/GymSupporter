package com.lefarmico.domain.repository.manager

import io.reactivex.rxjava3.core.Single

interface WorkoutTitleManager {

    fun getTitle(): Single<String>

    fun setTitle(title: String): Single<String>

    fun clearCache()
}
