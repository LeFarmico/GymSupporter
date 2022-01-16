package com.lefarmico.navigation.dialog

import com.lefarmico.navigation.params.SetParameterParams
import java.time.LocalDate
import java.time.LocalTime

sealed class Dialog {

    data class CalendarPickerDialog(
        val dateParameter: LocalDate,
        val callback: (LocalDate) -> Unit
    ) : Dialog()

    data class FieldEditorDialog(
        val hint: String,
        val callback: (String) -> Unit
    ) : Dialog()

    data class SetParameterPickerDialog(
        val exerciseId: Int,
        val callback: (SetParameterParams) -> Unit
    ) : Dialog()

    data class ListItemPickerDialog(
        val itemList: List<String>,
        val callbackPosition: (Int) -> Unit
    ) : Dialog()

    data class TimePickerDialog(
        val timeParameter: LocalTime,
        val callback: (LocalTime) -> Unit
    ) : Dialog()
}
