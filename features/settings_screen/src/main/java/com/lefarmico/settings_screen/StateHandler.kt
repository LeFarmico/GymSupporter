package com.lefarmico.settings_screen

import com.lefarmico.domain.entity.FormatterDto
import java.time.LocalDate

fun FormatterDto.reduce(): SettingsScreenState.CurrentFullDateFormatterResult {
    val currentDate = LocalDate.now()
    return SettingsScreenState.CurrentFullDateFormatterResult(currentDate.format(this.formatter))
}
