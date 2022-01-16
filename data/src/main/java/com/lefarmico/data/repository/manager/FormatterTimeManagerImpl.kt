package com.lefarmico.data.repository.manager

import com.lefarmico.data.db.FormatterTimeCache
import com.lefarmico.data.mapper.toDto
import com.lefarmico.domain.entity.FormatterDto
import com.lefarmico.domain.preferences.FormatterTimePreferenceHelper
import com.lefarmico.domain.repository.manager.FormatterTimeManager
import io.reactivex.rxjava3.core.Single
import javax.inject.Inject

class FormatterTimeManagerImpl @Inject constructor(
    private val helper: FormatterTimePreferenceHelper,
    private val cache: FormatterTimeCache
) : FormatterTimeManager {

    override fun getTimeFormatters(): Single<List<FormatterDto>> {
        return Single.create { emitter ->
            val formatterDataList = cache.getFormattersDataList()
            emitter.onSuccess(formatterDataList.toDto())
        }
    }

    override fun getSelectedTimeFormatter(): Single<FormatterDto> {
        return Single.create { emitter ->
            val selectedId = helper.getFormatterRes()
            val selectedFormatterData = cache.getFormatterById(selectedId)
            emitter.onSuccess(selectedFormatterData.toDto())
        }
    }

    override fun selectTimeFormatter(formatterDto: FormatterDto) {
        helper.setFormatterRes(formatterDto.id)
    }
}
