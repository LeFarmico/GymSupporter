package com.lefarmico.settings_screen

import com.lefarmico.domain.entity.FormatterDto
import com.lefarmico.domain.entity.RemindTimeDto
import java.time.LocalDate

fun FormatterDto.reduceFull(): SettingsScreenState.CurrentFullDateFormatterResult {
    val currentDate = LocalDate.now()
    return SettingsScreenState.CurrentFullDateFormatterResult(currentDate.format(formatter))
}

fun FormatterDto.reduceMY(): SettingsScreenState.CurrentMonthYearFormatterResult {
    val currentDate = LocalDate.now()
    return SettingsScreenState.CurrentMonthYearFormatterResult(currentDate.format(formatter))
}
fun RemindTimeDto.reduce(): SettingsScreenState.CurrentRemindTimeResult {
    return SettingsScreenState.CurrentRemindTimeResult(this.hoursBefore)
}
