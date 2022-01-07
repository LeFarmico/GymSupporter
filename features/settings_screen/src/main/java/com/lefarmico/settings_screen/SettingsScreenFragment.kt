package com.lefarmico.settings_screen

import com.lefarmico.core.base.BaseFragment
import com.lefarmico.settings_screen.SettingsScreenIntent.*
import com.lefarmico.settings_screen.databinding.FragmentSettingsBinding

class SettingsScreenFragment : BaseFragment<
    SettingsScreenIntent, SettingsScreenAction, SettingsScreenState, SettingsScreenEvent,
    FragmentSettingsBinding, SettingsScreenViewModel>(
    FragmentSettingsBinding::inflate,
    SettingsScreenViewModel::class.java
) {

    private val dateFormatFull get() = binding.dateFormatFull
    private val dateFormatMY get() = binding.dateFormatMonthYear

    override fun setUpViews() {

        dispatchIntent(GetCurrentDateFormatter)

        dateFormatFull.titleTextView.text = "Full date format"
        dateFormatMY.titleTextView.text = "Month Year format"

        dateFormatFull.setOnClickListener {
            dispatchIntent(SetFullDateFormatter)
        }
    }

    private fun setCurrentFullDateFormat(date: String) {
        dateFormatFull.summaryTextView.text = date
    }

    override fun receive(state: SettingsScreenState) {
        when (state) {
            is SettingsScreenState.CurrentFullDateFormatterResult -> setCurrentFullDateFormat(state.date)
        }
    }

    override fun receive(event: SettingsScreenEvent) {}
}
