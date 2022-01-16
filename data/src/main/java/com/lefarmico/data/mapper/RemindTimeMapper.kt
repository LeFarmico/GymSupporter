package com.lefarmico.data.mapper

import com.lefarmico.data.db.entity.RemindTimeData
import com.lefarmico.domain.entity.RemindTimeDto

fun RemindTimeData.toDto() = RemindTimeDto(id, hoursBefore)
fun List<RemindTimeData>.toDto() = this.map { it.toDto() }
