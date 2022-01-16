package com.lefarmico.settings_screen

import com.lefarmico.core.base.BaseFragment
import com.lefarmico.settings_screen.SettingsScreenIntent.*
import com.lefarmico.settings_screen.databinding.FragmentSettingsBinding

class SettingsScreenFragment : BaseFragment<
    SettingsScreenIntent, SettingsScreenState, SettingsScreenEvent,
    FragmentSettingsBinding, SettingsScreenViewModel>(
    FragmentSettingsBinding::inflate,
    SettingsScreenViewModel::class.java
) {

    private val dateFormatFull get() = binding.dateFormatFull
    private val dateFormatMY get() = binding.dateFormatMonthYear
    private val remindBeforeTime get() = binding.remindBeforeTime

    override fun setUpViews() {

        dispatchIntent(GetFullDateFormatter)
        dispatchIntent(GetMonthDateFormatter)
        dispatchIntent(GetRemindTime)

        dateFormatFull.titleTextView.text = getString(R.string.full_date_format)
        dateFormatMY.titleTextView.text = getString(R.string.month_year_date_format)
        remindBeforeTime.titleTextView.text = getText(R.string.workout_remind)

        dateFormatFull.setOnClickListener {
            dispatchIntent(SetFullDateFormatter)
        }
        dateFormatMY.setOnClickListener {
            dispatchIntent(SetMonthDateFormatter)
        }
        remindBeforeTime.setOnClickListener {
            dispatchIntent(SetRemindTime)
        }
    }

    private fun setFullDateFormat(date: String) {
        dateFormatFull.summaryTextView.text = date
    }
    private fun setMonthYearFormat(date: String) {
        dateFormatMY.summaryTextView.text = date
    }
    private fun setRemindTimeSettings(hoursBefore: Int) {
        val string = getString(R.string.remind_workout_before, hoursBefore)
        remindBeforeTime.summaryTextView.text = string
    }

    override fun receive(state: SettingsScreenState) {
        when (state) {
            is SettingsScreenState.CurrentFullDateFormatterResult -> setFullDateFormat(state.date)
            is SettingsScreenState.CurrentMonthYearFormatterResult -> setMonthYearFormat(state.date)
            is SettingsScreenState.CurrentRemindTimeResult -> setRemindTimeSettings(state.hours)
        }
    }

    override fun receive(event: SettingsScreenEvent) {}
}
