package com.lefarmico.settings_screen

import android.util.Log
import com.lefarmico.core.base.BaseViewModel
import com.lefarmico.core.extensions.observeUi
import com.lefarmico.domain.repository.manager.FormatterManager
import com.lefarmico.domain.repository.manager.FormatterMonthManager
import com.lefarmico.domain.repository.manager.RemindTimeManager
import com.lefarmico.domain.repository.manager.ThemeManager
import com.lefarmico.navigation.Router
import com.lefarmico.navigation.dialog.Dialog
import com.lefarmico.settings_screen.SettingsScreenIntent.*
import java.time.LocalDate
import javax.inject.Inject

class SettingsScreenViewModel @Inject constructor(
    private val formatterManager: FormatterManager,
    private val formatterMonthManager: FormatterMonthManager,
    private val remindTimeManager: RemindTimeManager,
    private val router: Router,
    private val themeManager: ThemeManager
) : BaseViewModel<
    SettingsScreenIntent, SettingsScreenState, SettingsScreenEvent
    >() {

    private fun setFullDateFormatter() {
        formatterManager.getDateFormatters()
            .observeUi()
            .doAfterSuccess { dto ->
                val date = LocalDate.now()
                val stringList = dto.map { date.format(it.formatter) }
                val dialog = Dialog.ListItemPickerDialog(stringList) {
                    formatterManager.selectFormatter(dto[it])
                    getCurrentFullDateFormat()
                }
                router.showDialog(dialog)
            }.subscribe()
    }

    private fun setMonthYearFormatter() {
        formatterMonthManager.getMonthFormatters()
            .observeUi()
            .doAfterSuccess { dto ->
                val date = LocalDate.now()
                val stringList = dto.map { date.format(it.formatter) }
                val dialog = Dialog.ListItemPickerDialog(stringList) {
                    formatterMonthManager.selectMonthFormatter(dto[it])
                    getCurrentMonthYearFormatter()
                }
                router.showDialog(dialog)
            }.subscribe()
    }

    private fun setRemindTime() {
        remindTimeManager.getRemindTimeList()
            .observeUi()
            .doAfterSuccess { dto ->
                val stringList = dto.map { it.hoursBefore.toString() }
                val dialog = Dialog.ListItemPickerDialog(stringList) {
                    remindTimeManager.selectRemindTime(dto[it])
                    getCurrentTimeRemind()
                }
                router.showDialog(dialog)
            }.subscribe()
    }

    private fun getCurrentTimeRemind() {
        remindTimeManager.getSelectedRemindTime()
            .observeUi()
            .doAfterSuccess { remindDto -> mState.value = remindDto.reduce() }
            .subscribe()
    }
    private fun getCurrentFullDateFormat() {
        formatterManager.getSelectedFormatter()
            .observeUi()
            .doAfterSuccess { formatterDto -> mState.value = formatterDto.reduceFull() }
            .subscribe()
    }

    private fun getCurrentMonthYearFormatter() {
        formatterMonthManager.getSelectedMonthFormatter()
            .observeUi()
            .doAfterSuccess { formatterDto ->
                mState.value = formatterDto.reduceMY()
            }.subscribe()
    }

    private fun getCurrentTheme() {
        themeManager.getCurrentTheme()
            .observeUi()
            .doAfterSuccess { themeId ->
                mEvent.setValue(SettingsScreenEvent.SetThemeMenuItem(themeId))
            }.subscribe()
    }

    private fun setCurrentTheme(themeId: Int) {
        themeManager.getCurrentTheme()
            .observeUi()
            .doAfterSuccess { oldThemeId ->
                if (themeId != oldThemeId) {
                    themeManager.setCurrentTheme(themeId)
                    mEvent.setValue(SettingsScreenEvent.ChangeTheme(themeId))
                }
            }.subscribe()
    }

    override fun triggerIntent(intent: SettingsScreenIntent) {
        return when (intent) {
            GetFullDateFormatter -> getCurrentFullDateFormat()
            SetFullDateFormatter -> setFullDateFormatter()
            GetMonthDateFormatter -> getCurrentMonthYearFormatter()
            SetMonthDateFormatter -> setMonthYearFormatter()
            GetRemindTime -> getCurrentTimeRemind()
            SetRemindTime -> setRemindTime()
            GetThemeSetting -> getCurrentTheme()
            is SetTheme -> setCurrentTheme(intent.themeId)
        }
    }
}
