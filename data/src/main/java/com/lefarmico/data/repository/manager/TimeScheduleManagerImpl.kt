package com.lefarmico.data.repository.manager

import com.lefarmico.data.db.WorkoutSwitchTimeCache
import com.lefarmico.data.extensions.dataStateResolver
import com.lefarmico.domain.repository.manager.TimeScheduleManager
import com.lefarmico.domain.utils.DataState
import io.reactivex.rxjava3.core.Single
import java.time.LocalTime
import java.time.format.DateTimeFormatter
import javax.inject.Inject

class TimeScheduleManagerImpl @Inject constructor(
    private val workoutTimeCache: WorkoutSwitchTimeCache
) : TimeScheduleManager {

    override fun getTime(): Single<DataState<LocalTime>> {
        return workoutTimeCache.getTime().map { localTime -> dataStateResolver { localTime } }
    }

    override fun getFormattedTime(formatter: DateTimeFormatter): Single<DataState<String>> {
        return workoutTimeCache.getTime().map { localTime ->
            dataStateResolver { localTime.format(formatter) }
        }
    }

    override fun setTime(localTime: LocalTime): Single<DataState<LocalTime>> {
        return workoutTimeCache.setAndGetTime(localTime)
            .map { newLocalTime -> dataStateResolver { newLocalTime } }
    }

    override fun setAndGetFormattedTime(
        localTime: LocalTime,
        formatter: DateTimeFormatter
    ): Single<DataState<String>> {
        return workoutTimeCache.setAndGetTime(localTime)
            .map { newLocalTime -> dataStateResolver { newLocalTime.format(formatter) } }
    }

    override fun clearCache() {
        workoutTimeCache.clearCache()
    }
}
