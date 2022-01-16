package com.lefarmico.data.db

import io.reactivex.rxjava3.core.Single
import java.time.LocalTime

class WorkoutSwitchTimeCache {

    private val lock = Any()
    private var localTime: LocalTime = LocalTime.now()
        get() {
            return when (isFirstRequest) {
                false -> field
                true -> {
                    isFirstRequest = false
                    LocalTime.now()
                }
            }
        }
    private var isFirstRequest = true

    fun getTime(): Single<LocalTime> {
        return Single.create { emitter ->
            synchronized(lock) {
                emitter.onSuccess(localTime)
            }
        }
    }

    fun setAndGetTime(time: LocalTime): Single<LocalTime> {
        return Single.create { emitter ->
            synchronized(lock) {
                localTime = time
                emitter.onSuccess(localTime)
            }
        }
    }

    fun clearCache() {
        synchronized(lock) {
            isFirstRequest = true
        }
    }
}
