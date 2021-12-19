package com.lefarmico.data.repository

import com.lefarmico.data.db.LocalDateTimeCache
import com.lefarmico.data.db.dao.WorkoutRecordsDao
import com.lefarmico.data.extensions.dataStateActionResolver
import com.lefarmico.data.utils.getMonthDatesInRange
import com.lefarmico.domain.entity.CalendarItemDto
import com.lefarmico.domain.repository.DateTimeManager
import com.lefarmico.domain.utils.DataState
import io.reactivex.rxjava3.core.Single
import java.lang.Exception
import java.time.LocalDate
import java.time.format.DateTimeFormatter
import java.util.*
import javax.inject.Inject

class DateTimeManagerImpl @Inject constructor(
    private val dao: WorkoutRecordsDao,
    private val dateTimeCache: LocalDateTimeCache
) : DateTimeManager {

    private val formatter = DateTimeFormatter.ofPattern("MMMM yyyy", Locale.getDefault())

    override fun getCurrentDaysInMonth(): Single<DataState<List<CalendarItemDto>>> {
        return dateTimeCache.currentMonth()
            .doOnError { DataState.Error(it as Exception) }
            .flatMap { localDate ->

                val days = localDate.getMonthDatesInRange()
                val from = localDate.withDayOfMonth(1)
                val to = localDate.withDayOfMonth(localDate.lengthOfMonth())

                dao.getWorkoutDateByTime(from, to).map { data ->
                    dataStateActionResolver {
                        days.map { date ->
                            when (data.any { date == it }) {
                                true -> CalendarItemDto(date, true)
                                false -> CalendarItemDto(date, false)
                            }
                        }
                    }
                }
            }
    }
    override fun currentMonth(): Single<DataState<LocalDate>> {
        return dateTimeCache.currentMonth()
            .doOnError { DataState.Error(it as Exception) }
            .map { dataStateActionResolver { it } }
    }

    override fun nextMonth(): Single<DataState<LocalDate>> {
        return dateTimeCache.nextMonth()
            .doOnError { DataState.Error(it as Exception) }
            .map { dataStateActionResolver { it } }
    }

    override fun prevMonth(): Single<DataState<LocalDate>> {
        return dateTimeCache.prevMonth()
            .doOnError { DataState.Error(it as Exception) }
            .map { dataStateActionResolver { it } }
    }

    override fun getSelectedDate(): Single<DataState<LocalDate>> {
        return dateTimeCache.getClickedDate()
            .doOnError { DataState.Error(it as Exception) }
            .map { dataStateActionResolver { it } }
    }

    override fun selectDate(localDate: LocalDate): Single<DataState<LocalDate>> {
        return dateTimeCache.setClickedDate(localDate)
            .doOnError { DataState.Error(it as Exception) }
            .map { dataStateActionResolver { it } }
    }
}
