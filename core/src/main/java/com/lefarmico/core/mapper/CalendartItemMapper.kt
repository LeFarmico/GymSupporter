package com.lefarmico.core.mapper

import com.lefarmico.core.entity.CalendarItemViewData
import com.lefarmico.domain.entity.CalendarItemDto

/**
 *  Converts the [CalendarItemDto] to the [CalendarItemViewData]
 *  as a new instance.
 *
 *  Used as ViewData entity
 */
fun CalendarItemDto.toViewData() = CalendarItemViewData(date, isChecked)

/**
 *  Converts a [List] of the [CalendarItemDto] to a [List] of the [CalendarItemViewData]
 *  as a new instance.
 *
 *  Used as ViewData entity
 */
fun List<CalendarItemDto>.toViewData() = this.map { it.toViewData() }
