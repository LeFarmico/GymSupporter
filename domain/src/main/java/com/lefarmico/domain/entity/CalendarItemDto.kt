package com.lefarmico.domain.entity

import java.time.LocalDateTime

data class CalendarItemDto(
    val date: LocalDateTime,
    val isChecked: Boolean = false
)
