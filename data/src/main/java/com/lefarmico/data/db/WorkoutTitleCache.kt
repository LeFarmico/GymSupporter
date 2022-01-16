package com.lefarmico.data.db

import io.reactivex.rxjava3.core.Single
import javax.inject.Inject

class WorkoutTitleCache @Inject constructor() {

    private val lock = Any()

    // TODO прокидывать через ресурсы
    private var title: String = "Your workout"

    fun getCachedTitle(): Single<String> {
        return Single.create { emitter ->
            synchronized(lock) {
                emitter.onSuccess(title)
            }
        }
    }

    fun setCachedTitle(title: String): Single<String> {
        return Single.create { emitter ->
            synchronized(lock) {
                this.title = title
                emitter.onSuccess(title)
            }
        }
    }

    fun clearCache() {
        synchronized(lock) {
            title = "Your workout"
        }
    }
}
