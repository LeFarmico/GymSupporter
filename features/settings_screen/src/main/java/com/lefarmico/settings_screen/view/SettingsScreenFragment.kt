package com.lefarmico.settings_screen.view

import android.os.Bundle
import com.lefarmico.core.base.BaseFragment
import com.lefarmico.settings_screen.databinding.FragmentSettingsBinding
import com.lefarmico.settings_screen.intent.SettingsScreenIntent
import com.lefarmico.settings_screen.viewModel.SettingsScreenViewModel

class SettingsScreenFragment : BaseFragment<FragmentSettingsBinding, SettingsScreenViewModel>(
    FragmentSettingsBinding::inflate,
    SettingsScreenViewModel::class.java
) {

    private val dateFormatFull get() = binding.dateFormatFull
    private val dateFormatMY get() = binding.dateFormatMonthYear

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun setUpViews() {

        viewModel.onTriggerEvent(SettingsScreenIntent.GetCurrentDateFormatter)

        dateFormatFull.titleTextView.text = "Full date format"
        dateFormatMY.titleTextView.text = "Month Year format"

        dateFormatFull.setOnClickListener {
            viewModel.onTriggerEvent(SettingsScreenIntent.SetFullDateFormatter)
        }
    }

    override fun observeData() {
        viewModel.currentFullDateFormatter.observe(viewLifecycleOwner) {
            dateFormatFull.summaryTextView.text = it
        }
    }
}
