package com.lefarmico.data.repository.manager

import com.lefarmico.data.db.WorkoutTitleCache
import com.lefarmico.domain.repository.manager.WorkoutTitleManager
import io.reactivex.rxjava3.core.Single
import javax.inject.Inject

class WorkoutTitleManagerImpl @Inject constructor(
    private val cache: WorkoutTitleCache
) : WorkoutTitleManager {

    override fun getTitle(): Single<String> {
        return cache.getCachedTitle()
    }

    override fun setTitle(title: String): Single<String> {
        return cache.setCachedTitle(title)
    }

    override fun clearCache() {
        cache.clearCache()
    }
}
