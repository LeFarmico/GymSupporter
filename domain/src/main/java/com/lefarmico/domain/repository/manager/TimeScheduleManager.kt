package com.lefarmico.domain.repository.manager

import com.lefarmico.domain.utils.DataState
import io.reactivex.rxjava3.core.Single
import java.time.LocalTime
import java.time.format.DateTimeFormatter

interface TimeScheduleManager {

    fun getTime(): Single<DataState<LocalTime>>

    fun getFormattedTime(formatter: DateTimeFormatter): Single<DataState<String>>

    fun setTime(localTime: LocalTime): Single<DataState<LocalTime>>

    fun setAndGetFormattedTime(localTime: LocalTime, formatter: DateTimeFormatter): Single<DataState<String>>

    fun clearCache()
}
