package com.lefarmico.data.repository

import com.lefarmico.data.db.dao.WorkoutRecordsDao
import com.lefarmico.data.extensions.dataStateActionResolver
import com.lefarmico.data.utils.getMonthDatesInRange
import com.lefarmico.domain.entity.CalendarItemDto
import com.lefarmico.domain.repository.CalendarRepository
import com.lefarmico.domain.utils.DataState
import io.reactivex.rxjava3.core.Single
import java.lang.Exception
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter
import java.util.*
import javax.inject.Inject

class CalendarRepositoryImpl @Inject constructor(
    private val dao: WorkoutRecordsDao
) : CalendarRepository {

    override fun getDaysByDate(date: LocalDateTime): Single<DataState<List<CalendarItemDto>>> {
        val localDate = date.toLocalDate()

        val days = localDate.getMonthDatesInRange()
        val from = localDate.withDayOfMonth(1).atStartOfDay()
        val to = localDate.withDayOfMonth(localDate.lengthOfMonth()).atStartOfDay()

        return dao.getWorkoutDateByTime(from, to)
            .doOnError { DataState.Error(it as Exception) }
            .map { data ->
                dataStateActionResolver {
                    days.map { date ->
                        when (data.any { date.isEqual(it) }) {
                            true -> CalendarItemDto(date, true)
                            false -> CalendarItemDto(date, false)
                        }
                    }
                }
            }
    }

    override fun getCurrentMonthAndYear(date: LocalDateTime): Single<DataState<String>> {
        val formatter = DateTimeFormatter.ofPattern("MMMM yyyy", Locale.getDefault())
        val month = date.format(formatter)
        return Single.create<DataState<String>> {
            it.onSuccess(dataStateActionResolver { month })
        }.doOnError { DataState.Error(it as Exception) }
    }
}
