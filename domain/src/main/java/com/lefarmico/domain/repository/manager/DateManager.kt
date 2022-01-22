package com.lefarmico.domain.repository.manager

import com.lefarmico.domain.entity.CalendarItemDto
import com.lefarmico.domain.utils.DataState
import io.reactivex.rxjava3.core.Single
import java.time.LocalDate
import java.time.format.DateTimeFormatter

interface DateManager {

    fun getCurrentDaysInMonth(): Single<DataState<List<CalendarItemDto>>>

    fun currentMonth(): Single<DataState<LocalDate>>

    fun nextMonth(): Single<DataState<LocalDate>>

    fun prevMonth(): Single<DataState<LocalDate>>

    fun getSelectedDate(): Single<DataState<LocalDate>>

    fun getSelectedDateFormatted(formatter: DateTimeFormatter): Single<DataState<String>>

    fun setAndGetFormattedDate(localDate: LocalDate, formatter: DateTimeFormatter): Single<DataState<String>>

    fun selectDate(localDate: LocalDate): Single<DataState<LocalDate>>

    fun selectMonth(localDate: LocalDate): Single<DataState<LocalDate>>
}
