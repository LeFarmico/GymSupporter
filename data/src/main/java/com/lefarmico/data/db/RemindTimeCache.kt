package com.lefarmico.data.db

import androidx.annotation.IntDef
import com.lefarmico.data.db.entity.RemindTimeData

class RemindTimeCache {

    private val lock = Any()
    private val remindTimeSet = mutableSetOf<RemindTimeData>()

    init {
        remindTimeSet.add(RemindTimeData(PERMANENTLY, 0))
        remindTimeSet.add(RemindTimeData(BEFORE_HOUR, 1))
        remindTimeSet.add(RemindTimeData(BEFORE_TWO_HOURS, 2))
        remindTimeSet.add(RemindTimeData(BEFORE_THREE_HOURS, 3))
        remindTimeSet.add(RemindTimeData(BEFORE_SIX_HOURS, 6))
        remindTimeSet.add(RemindTimeData(BEFORE_DAY, 24))
    }

    fun getRemindTimeById(@RemindTimeId id: Int): RemindTimeData {
        synchronized(lock) {
            return remindTimeSet.find { it.id == id }
                ?: throw NullPointerException("that remindTime is not exist")
        }
    }

    fun getRemindTimeList(): List<RemindTimeData> {
        synchronized(lock) {
            return remindTimeSet.toList()
        }
    }

    companion object {

        @IntDef(
            PERMANENTLY, BEFORE_HOUR, BEFORE_TWO_HOURS,
            BEFORE_THREE_HOURS, BEFORE_SIX_HOURS, BEFORE_DAY
        )
        @Retention(AnnotationRetention.SOURCE)
        annotation class RemindTimeId

        const val PERMANENTLY = 0
        const val BEFORE_HOUR = 1
        const val BEFORE_TWO_HOURS = 2
        const val BEFORE_THREE_HOURS = 3
        const val BEFORE_SIX_HOURS = 6
        const val BEFORE_DAY = 24
    }
}
