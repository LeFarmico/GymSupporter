package com.lefarmico.data.mapper

import com.lefarmico.data.db.entity.FormatterData
import com.lefarmico.domain.entity.FormatterDto

fun FormatterDto.toData(): FormatterData = FormatterData(id, formatter)

fun FormatterData.toDto(): FormatterDto = FormatterDto(id, formatter)

fun List<FormatterData>.toDtoList(): List<FormatterDto> = this.map { it.toDto() }

fun List<FormatterDto>.toDataList(): List<FormatterData> = this.map { it.toData() }
