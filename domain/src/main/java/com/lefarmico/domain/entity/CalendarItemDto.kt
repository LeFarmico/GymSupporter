package com.lefarmico.domain.entity

import java.time.LocalDate

data class CalendarItemDto(
    val date: LocalDate,
    val isChecked: Boolean = false
)
