package com.lefarmico.domain.repository

import com.lefarmico.domain.entity.FormatterDto
import io.reactivex.rxjava3.core.Single

interface FormatterManager {

    // formatter for full date as 20.10.1999
    fun getDateFormatters(): Single<List<FormatterDto>>

    fun getSelectedFormatter(): Single<FormatterDto>

    fun selectFormatter(formatterDto: FormatterDto)
}
