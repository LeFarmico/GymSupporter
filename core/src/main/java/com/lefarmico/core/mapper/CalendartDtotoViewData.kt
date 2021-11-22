package com.lefarmico.core.mapper

import com.lefarmico.core.entity.CalendarItemViewData
import com.lefarmico.domain.entity.CalendarItemDto

fun CalendarItemDto.toViewData() = CalendarItemViewData(date, isChecked)

fun List<CalendarItemDto>.toViewData() = this.map { it.toViewData() }
