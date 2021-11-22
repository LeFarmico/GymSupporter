package com.lefarmico.core.entity

import java.time.LocalDateTime

data class CalendarItemViewData(
    val date: LocalDateTime,
    val isChecked: Boolean = false
)
