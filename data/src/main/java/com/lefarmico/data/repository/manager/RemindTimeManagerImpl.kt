package com.lefarmico.data.repository.manager

import com.lefarmico.data.db.RemindTimeCache
import com.lefarmico.data.mapper.toDto
import com.lefarmico.domain.entity.RemindTimeDto
import com.lefarmico.domain.preferences.RemindPreferenceHelper
import com.lefarmico.domain.repository.manager.RemindTimeManager
import io.reactivex.rxjava3.core.Single
import javax.inject.Inject

class RemindTimeManagerImpl @Inject constructor(
    private val helper: RemindPreferenceHelper,
    private val cache: RemindTimeCache
) : RemindTimeManager {

    override fun getRemindTimeList(): Single<List<RemindTimeDto>> {
        return Single.create { emitter ->
            val remindTimeDataList = cache.getRemindTimeList()
            emitter.onSuccess(remindTimeDataList.toDto())
        }
    }

    override fun getSelectedRemindTime(): Single<RemindTimeDto> {
        return Single.create { emitter ->
            val selectedId = helper.getBeforeRemindTimeRes()
            val selectedRemindTimeData = cache.getRemindTimeById(selectedId)
            emitter.onSuccess(selectedRemindTimeData.toDto())
        }
    }

    override fun selectRemindTime(remindTimeDto: RemindTimeDto) {
        helper.setBeforeRemindTimeRes(remindTimeDto.id)
    }
}
