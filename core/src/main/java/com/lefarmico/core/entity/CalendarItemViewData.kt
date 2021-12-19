package com.lefarmico.core.entity

import java.time.LocalDate

data class CalendarItemViewData(
    val date: LocalDate,
    val isChecked: Boolean = false
)
