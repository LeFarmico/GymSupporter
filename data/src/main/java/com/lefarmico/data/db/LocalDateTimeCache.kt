package com.lefarmico.data.db

import com.lefarmico.data.utils.getMonthDatesInRange
import io.reactivex.rxjava3.core.Single
import java.time.LocalDate

class LocalDateTimeCache {

    private var clickedDate = LocalDate.now()
    private var clickedMonth = LocalDate.now()
    private val daysInMonth get() = clickedMonth.getMonthDatesInRange()

    fun nextMonth(): Single<LocalDate> {
        return Single.create {
            clickedMonth = clickedMonth.plusMonths(1)
            it.onSuccess(clickedMonth)
        }
    }

    fun prevMonth(): Single<LocalDate> {
        return Single.create {
            clickedMonth = clickedMonth.minusMonths(1)
            it.onSuccess(clickedMonth)
        }
    }

    fun currentMonth(): Single<LocalDate> {
        return Single.create {
            it.onSuccess(clickedMonth)
        }
    }

    fun getClickedDate(): Single<LocalDate> {
        return Single.create {
            it.onSuccess(clickedDate)
        }
    }

    fun setClickedDate(localDate: LocalDate): Single<LocalDate> {
        return Single.create {
            clickedDate = localDate
            it.onSuccess(localDate)
        }
    }

    fun getDaysInMonth(): Single<List<LocalDate>> {
        return Single.create {
            it.onSuccess(daysInMonth)
        }
    }
}
