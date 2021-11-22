package com.lefarmico.domain.repository

import com.lefarmico.domain.entity.CalendarItemDto
import com.lefarmico.domain.utils.DataState
import io.reactivex.rxjava3.core.Single
import java.time.LocalDateTime

interface CalendarRepository {

    fun getDaysByDate(date: LocalDateTime): Single<DataState<List<CalendarItemDto>>>

    fun getCurrentMonthAndYear(date: LocalDateTime): Single<DataState<String>>
}
