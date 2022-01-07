package com.lefarmico.settings_screen

import com.lefarmico.core.base.BaseViewModel
import com.lefarmico.core.extensions.observeUi
import com.lefarmico.domain.repository.FormatterManager
import com.lefarmico.navigation.Router
import com.lefarmico.navigation.dialog.Dialog
import com.lefarmico.settings_screen.SettingsScreenIntent.*
import java.time.LocalDate
import javax.inject.Inject

class SettingsScreenViewModel @Inject constructor() : BaseViewModel<
    SettingsScreenIntent, SettingsScreenAction, SettingsScreenState, SettingsScreenEvent
    >() {

    @Inject lateinit var formatterManager: FormatterManager
    @Inject lateinit var router: Router

    private fun getFullDateTimeFormatters() {
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

    private fun getCurrentFullDateFormat() {
        formatterManager.getSelectedFormatter()
            .observeUi()
            .doAfterSuccess { formatterDto -> mState.value = formatterDto.reduce() }
            .subscribe()
    }

    override fun triggerAction(action: SettingsScreenAction) {
        when (action) {
            SettingsScreenAction.GetCurrentDateFormatter -> getCurrentFullDateFormat()
            SettingsScreenAction.SetFullDateFormatter -> getFullDateTimeFormatters()
        }
    }

    override fun intentToAction(intent: SettingsScreenIntent): SettingsScreenAction {
        return when (intent) {
            GetCurrentDateFormatter -> SettingsScreenAction.GetCurrentDateFormatter
            SetFullDateFormatter -> SettingsScreenAction.SetFullDateFormatter
        }
    }
}
