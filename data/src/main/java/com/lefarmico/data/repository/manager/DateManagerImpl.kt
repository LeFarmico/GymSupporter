package com.lefarmico.data.repository.manager

import com.lefarmico.data.db.LocalDateCache
import com.lefarmico.data.db.dao.WorkoutRecordsDao
import com.lefarmico.data.extensions.dataStateResolver
import com.lefarmico.data.extensions.getMonthDatesInRange
import com.lefarmico.domain.entity.CalendarItemDto
import com.lefarmico.domain.repository.manager.DateManager
import com.lefarmico.domain.utils.DataState
import io.reactivex.rxjava3.core.Single
import java.time.LocalDate
import java.time.format.DateTimeFormatter
import javax.inject.Inject

class DateManagerImpl @Inject constructor(
    private val dao: WorkoutRecordsDao,
    private val dateCache: LocalDateCache
) : DateManager {

    override fun getCurrentDaysInMonth(): Single<DataState<List<CalendarItemDto>>> {
        return dateCache.currentMonth()
            .flatMap { localDate ->

                val days = localDate.getMonthDatesInRange()
                val from = localDate.withDayOfMonth(1)
                val to = localDate.withDayOfMonth(localDate.lengthOfMonth())

                dao.getWorkoutDateByTime(from, to).map { data ->
                    dataStateResolver {
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
        return dateCache.currentMonth()
            .map { dataStateResolver { it } }
    }

    override fun nextMonth(): Single<DataState<LocalDate>> {
        return dateCache.nextMonth()
            .map { dataStateResolver { it } }
    }

    override fun prevMonth(): Single<DataState<LocalDate>> {
        return dateCache.prevMonth()
            .map { dataStateResolver { it } }
    }

    override fun getSelectedDate(): Single<DataState<LocalDate>> {
        return dateCache.getClickedDate()
            .map { dataStateResolver { it } }
    }

    override fun getSelectedDateFormatted(formatter: DateTimeFormatter): Single<DataState<String>> {
        return dateCache.getClickedDate()
            .map { dataStateResolver { it.format(formatter) } }
    }

    override fun setAndGetFormattedDate(localDate: LocalDate, formatter: DateTimeFormatter): Single<DataState<String>> {
        return dateCache.setClickedDate(localDate)
            .map { dataStateResolver { it.format(formatter) } }
    }

    override fun selectDate(localDate: LocalDate): Single<DataState<LocalDate>> {
        return dateCache.setClickedDate(localDate)
            .map { dataStateResolver { it } }
    }
}
