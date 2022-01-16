package com.lefarmico.core.dialog

import android.app.Dialog
import android.app.TimePickerDialog
import android.os.Bundle
import android.widget.TimePicker
import androidx.fragment.app.DialogFragment
import com.lefarmico.core.R
import java.time.LocalTime

class LocalTimePickerDialog(
    private val withTime: LocalTime = LocalTime.now(),
    private val callback: (LocalTime) -> Unit
) : DialogFragment(), TimePickerDialog.OnTimeSetListener {

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        return TimePickerDialog(
            requireContext(), R.style.TimePickerTheme, this,
            withTime.hour, withTime.minute, true
        )
    }

    override fun onTimeSet(view: TimePicker?, hourOfDay: Int, minute: Int) {
        callback(LocalTime.of(hourOfDay, minute))
    }

    companion object {
        const val TAG_DIALOG = "LocalTimePicker"
    }
}
