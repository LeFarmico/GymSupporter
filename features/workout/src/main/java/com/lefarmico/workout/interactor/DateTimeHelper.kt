package com.lefarmico.workout.interactor

import com.lefarmico.domain.repository.manager.DateManager
import com.lefarmico.domain.repository.manager.FormatterManager
import com.lefarmico.domain.repository.manager.FormatterTimeManager
import com.lefarmico.domain.repository.manager.TimeScheduleManager
import com.lefarmico.workout.WorkoutState
import com.lefarmico.workout.reduceDate
import com.lefarmico.workout.reduceTime
import io.reactivex.rxjava3.core.Single
import java.time.LocalDate
import java.time.LocalTime

class DateTimeHelper(
    private val dateManager: DateManager,
    private val timeManager: TimeScheduleManager,
    private val formatterManager: FormatterManager,
    private val formatterTimeManager: FormatterTimeManager
) {

    fun getDate(): Single<WorkoutState> {
        return formatterManager.getSelectedFormatter()
            .flatMap { dto ->
                dateManager.getSelectedDateFormatted(dto.formatter)
            }.map { date -> date.reduceDate() }
    }

    fun setDate(localDate: LocalDate): Single<WorkoutState> {
        return formatterManager.getSelectedFormatter()
            .flatMap { dto -> dateManager.setAndGetFormattedDate(localDate, dto.formatter) }
            .map { dataState -> dataState.reduceDate() }
    }

    fun getTime(): Single<WorkoutState> {
        return formatterTimeManager.getSelectedTimeFormatter()
            .flatMap { dto -> timeManager.getFormattedTime(dto.formatter) }
            .map { time -> time.reduceTime() }
    }

    fun setTime(localTime: LocalTime): Single<WorkoutState> {
        return formatterTimeManager.getSelectedTimeFormatter()
            .flatMap { dto -> timeManager.setAndGetFormattedTime(localTime, dto.formatter) }
            .map { time -> time.reduceTime() }
    }
}
