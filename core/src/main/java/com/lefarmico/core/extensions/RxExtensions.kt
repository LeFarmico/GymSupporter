package com.lefarmico.core.extensions

import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers
import io.reactivex.rxjava3.core.Single
import io.reactivex.rxjava3.schedulers.Schedulers

fun <T : Any> Single<T>.observeUi(): Single<T> {
    return this
        .observeOn(AndroidSchedulers.mainThread())
        .subscribeOn(Schedulers.io())
}
