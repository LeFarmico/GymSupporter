package com.lefarmico.settings_screen

import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.app.AppCompatDelegate
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
    private val themeMenu get() = binding.darkModeSelector

    override fun setUpViews() {
        dispatchIntent(GetFullDateFormatter)
        dispatchIntent(GetMonthDateFormatter)
        dispatchIntent(GetRemindTime)
        dispatchIntent(GetThemeSetting)
        setUpToolbar()

        dateFormatFull.titleTextView.text = getString(R.string.full_date_format)
        dateFormatMY.titleTextView.text = getString(R.string.month_year_date_format)
        remindBeforeTime.titleTextView.text = getText(R.string.workout_remind)
        themeMenu.title.text = getString(R.string.theme)
        themeMenu.lightTheme.text = getString(R.string.light_theme)
        themeMenu.nightTheme.text = getString(R.string.dark_theme)
        themeMenu.systemDefaultTheme.text = getString(R.string.theme_system_default)

        themeMenu.radioGroup.setOnCheckedChangeListener { radioGroup, i ->
            dispatchIntent(SetTheme(i))
        }

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

    private fun setUpToolbar() {
        requireActivity().title = getString(R.string.settings_screen)
        (requireActivity() as AppCompatActivity).supportActionBar?.setDisplayHomeAsUpEnabled(false)
        (requireActivity() as AppCompatActivity).supportActionBar?.setDisplayShowHomeEnabled(false)
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

    override fun receive(event: SettingsScreenEvent) {
        when (event) {
            is SettingsScreenEvent.ChangeTheme -> setTheme(event.themeId)
            is SettingsScreenEvent.SetThemeMenuItem -> setCheckMenuItem(event.themeId)
        }
    }

    private fun setTheme(themeId: Int) {
        Log.d("THEME_CHECK", AppCompatDelegate.getDefaultNightMode().toString())
        when (themeId) {
            R.id.system_default_theme -> {
                AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_FOLLOW_SYSTEM)
                Log.d("THEME_CHECK", "default theme")
            }
            R.id.night_theme -> {
                AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES)
                Log.d("THEME_CHECK", "night theme")
            }
            R.id.light_theme -> {
                AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO)
                Log.d("THEME_CHECK", "light theme")
            }
            else -> {}
        }
    }

    private fun setCheckMenuItem(themeId: Int) {
        when (themeId) {
            R.id.system_default_theme -> {
                themeMenu.systemDefaultTheme.isChecked = true
            }
            R.id.night_theme -> {
                themeMenu.nightTheme.isChecked = true
            }
            R.id.light_theme -> {
                themeMenu.lightTheme.isChecked = true
            }
            else -> {}
        }
    }
}
