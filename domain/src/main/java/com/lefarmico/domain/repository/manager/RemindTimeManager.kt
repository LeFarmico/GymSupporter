package com.lefarmico.domain.repository.manager

import com.lefarmico.domain.entity.RemindTimeDto
import io.reactivex.rxjava3.core.Single

interface RemindTimeManager {

    fun getRemindTimeList(): Single<List<RemindTimeDto>>

    fun getSelectedRemindTime(): Single<RemindTimeDto>

    fun selectRemindTime(remindTimeDto: RemindTimeDto)
}
