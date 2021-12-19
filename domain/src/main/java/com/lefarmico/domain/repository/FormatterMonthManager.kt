package com.lefarmico.domain.repository

import com.lefarmico.domain.entity.FormatterDto
import io.reactivex.rxjava3.core.Single

interface FormatterMonthManager {

    // formatter for full date as December 2020
    fun getMonthFormatters(): Single<List<FormatterDto>>

    fun getSelectedMonthFormatter(): Single<FormatterDto>

    fun selectMonthFormatter(formatterDto: FormatterDto)
}
