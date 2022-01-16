package com.lefarmico.core.extensions

import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers
import io.reactivex.rxjava3.core.Observable
import io.reactivex.rxjava3.core.Single
import io.reactivex.rxjava3.schedulers.Schedulers
import java.util.concurrent.TimeUnit

fun <T : Any> Single<T>.observeUi(): Single<T> {
    return this
        .observeOn(AndroidSchedulers.mainThread())
        .subscribeOn(Schedulers.io())
}
fun <T : Any> Observable<T>.observeUi(): Observable<T> {
    return this
        .observeOn(AndroidSchedulers.mainThread())
        .subscribeOn(Schedulers.io())
}

fun <T : Any> Single<T>.observeIO(): Single<T> {
    return this
        .observeOn(Schedulers.io())
        .subscribeOn(Schedulers.io())
}
fun <T : Any> Observable<T>.observeIO(): Observable<T> {
    return this
        .observeOn(Schedulers.io())
        .subscribeOn(Schedulers.io())
}

fun <T : Any> Observable<T>.debounceImmediate(timeout: Long, unit: TimeUnit): Observable<T> {
    return publish {
        it.take(1).concatWith(it.debounce(timeout, unit))
    }
}
