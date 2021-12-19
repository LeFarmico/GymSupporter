package com.lefarmico.domain.repository

import com.lefarmico.domain.entity.CalendarItemDto
import com.lefarmico.domain.utils.DataState
import io.reactivex.rxjava3.core.Single
import java.time.LocalDate

interface DateTimeManager {

    fun getCurrentDaysInMonth(): Single<DataState<List<CalendarItemDto>>>

    fun currentMonth(): Single<DataState<LocalDate>>

    fun nextMonth(): Single<DataState<LocalDate>>

    fun prevMonth(): Single<DataState<LocalDate>>

    fun getSelectedDate(): Single<DataState<LocalDate>>

    fun selectDate(localDate: LocalDate): Single<DataState<LocalDate>>
}
