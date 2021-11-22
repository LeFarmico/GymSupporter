package com.lefarmico.core.utils

import java.time.LocalDate
import java.time.LocalDateTime

object Utilities {

    fun getCurrentDate(): LocalDateTime = LocalDate.now().atStartOfDay()
}
