package com.lefarmico.core.dialog

import android.app.DatePickerDialog
import android.app.Dialog
import android.os.Bundle
import android.widget.DatePicker
import androidx.fragment.app.DialogFragment
import com.lefarmico.core.R
import java.time.LocalDate

class LocalDatePickerDialog(
    private val withDate: LocalDate = LocalDate.now(),
    private val callback: (LocalDate) -> Unit = {}
) : DialogFragment(), DatePickerDialog.OnDateSetListener {

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        return DatePickerDialog(
            requireContext(), R.style.DatePickerTheme, this,
            withDate.year, withDate.monthValue - 1, withDate.dayOfMonth
        )
    }

    override fun onDateSet(view: DatePicker?, year: Int, month: Int, dayOfMonth: Int) {
        callback(LocalDate.of(year, month + 1, dayOfMonth))
    }

    companion object {
        const val TAG_DIALOG = "LocalDatePicker"
    }
}
