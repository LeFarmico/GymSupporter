package com.lefarmico.domain.repository.manager

import com.lefarmico.domain.entity.FormatterDto
import io.reactivex.rxjava3.core.Single

interface FormatterTimeManager {

    fun getTimeFormatters(): Single<List<FormatterDto>>

    fun getSelectedTimeFormatter(): Single<FormatterDto>

    fun selectTimeFormatter(formatterDto: FormatterDto)
}
