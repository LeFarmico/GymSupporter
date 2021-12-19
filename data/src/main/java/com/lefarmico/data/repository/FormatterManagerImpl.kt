package com.lefarmico.data.repository

import com.lefarmico.data.db.FormatterCache
import com.lefarmico.data.mapper.toDto
import com.lefarmico.data.mapper.toDtoList
import com.lefarmico.domain.entity.FormatterDto
import com.lefarmico.domain.preferences.FormatterPreferenceHelper
import com.lefarmico.domain.repository.FormatterManager
import io.reactivex.rxjava3.core.Single
import javax.inject.Inject

class FormatterManagerImpl @Inject constructor(
    private val helper: FormatterPreferenceHelper,
    private val cache: FormatterCache
) : FormatterManager {

    override fun getDateFormatters(): Single<List<FormatterDto>> {
        return Single.create {
            val formatterDataList = cache.getFormattersDataList()
            it.onSuccess(formatterDataList.toDtoList())
        }
    }

    override fun getSelectedFormatter(): Single<FormatterDto> {
        return Single.create {
            val selectedId = helper.getFormatterRes()
            val selectedFormatterData = cache.getFormatterById(selectedId)
            it.onSuccess(selectedFormatterData.toDto())
        }
    }

    override fun selectFormatter(formatterDto: FormatterDto) {
        helper.setFormatterRes(formatterDto.id)
    }
}
