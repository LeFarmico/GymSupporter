package com.lefarmico.data.db

import io.reactivex.rxjava3.core.Single
import java.time.LocalDate

class LocalDateCache {

    private val lock = Any()
    private var clickedDate = LocalDate.now()
    private var clickedMonth = LocalDate.now()

    fun nextMonth(): Single<LocalDate> {
        return Single.create {
            synchronized(lock) {
                clickedMonth = clickedMonth.plusMonths(1)
                it.onSuccess(clickedMonth)
            }
        }
    }

    fun prevMonth(): Single<LocalDate> {
        return Single.create {
            synchronized(lock) {
                clickedMonth = clickedMonth.minusMonths(1)
                it.onSuccess(clickedMonth)
            }
        }
    }

    fun currentMonth(): Single<LocalDate> {
        return Single.create {
            synchronized(lock) {
                it.onSuccess(clickedMonth)
            }
        }
    }

    fun selectMonth(localDate: LocalDate): Single<LocalDate> {
        return Single.create {
            synchronized(lock) {
                clickedMonth = localDate
                it.onSuccess(clickedDate)
            }
        }
    }

    fun getClickedDate(): Single<LocalDate> {
        return Single.create {
            synchronized(lock) {
                it.onSuccess(clickedDate)
            }
        }
    }

    fun setClickedDate(localDate: LocalDate): Single<LocalDate> {
        return Single.create {
            synchronized(lock) {
                clickedDate = localDate
                it.onSuccess(localDate)
            }
        }
    }
}
