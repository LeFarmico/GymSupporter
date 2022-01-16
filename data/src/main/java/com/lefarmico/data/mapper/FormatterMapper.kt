package com.lefarmico.data.mapper

import com.lefarmico.data.db.entity.FormatterData
import com.lefarmico.domain.entity.FormatterDto

fun FormatterData.toDto(): FormatterDto = FormatterDto(id, formatter)

fun List<FormatterData>.toDto(): List<FormatterDto> = this.map { it.toDto() }
