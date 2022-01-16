package com.lefarmico.data.repository.manager

import com.lefarmico.data.db.FormatterMonthCache
import com.lefarmico.data.mapper.toDto
import com.lefarmico.domain.entity.FormatterDto
import com.lefarmico.domain.preferences.FormatterMonthPreferenceHelper
import com.lefarmico.domain.repository.manager.FormatterMonthManager
import io.reactivex.rxjava3.core.Single
import javax.inject.Inject

class FormatterMonthManagerImpl @Inject constructor(
    private val helper: FormatterMonthPreferenceHelper,
    private val cache: FormatterMonthCache
) : FormatterMonthManager {

    override fun getMonthFormatters(): Single<List<FormatterDto>> {
        return Single.create {
            val formatterMonthDataList = cache.getFormattersDataList()
            it.onSuccess(formatterMonthDataList.toDto())
        }
    }

    override fun getSelectedMonthFormatter(): Single<FormatterDto> {
        return Single.create {
            val selectedId = helper.getFormatterRes()
            val selectedFormatterMonthData = cache.getFormatterById(selectedId)
            it.onSuccess(selectedFormatterMonthData.toDto())
        }
    }

    override fun selectMonthFormatter(formatterDto: FormatterDto) {
        helper.setFormatterRes(formatterDto.id)
    }
}
