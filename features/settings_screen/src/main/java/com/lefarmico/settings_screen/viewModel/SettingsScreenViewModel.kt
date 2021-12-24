package com.lefarmico.settings_screen.viewModel

import androidx.lifecycle.MutableLiveData
import com.lefarmico.core.base.BaseViewModel
import com.lefarmico.core.extensions.observeUi
import com.lefarmico.domain.repository.FormatterManager
import com.lefarmico.navigation.Router
import com.lefarmico.navigation.dialog.Dialog
import com.lefarmico.settings_screen.intent.SettingsScreenIntent
import java.time.LocalDate
import javax.inject.Inject

class SettingsScreenViewModel @Inject constructor() : BaseViewModel<SettingsScreenIntent>() {

    @Inject lateinit var formatterManager: FormatterManager
    @Inject lateinit var router: Router

    val currentFullDateFormatter = MutableLiveData<String>()

    private fun getFullDateTimeFormatters() {
        formatterManager.getDateFormatters()
            .observeUi()
            .doAfterSuccess { dto ->
                val date = LocalDate.now()
                val stringList = dto.map { date.format(it.formatter) }
                val dialog = Dialog.ListItemPickerDialog(stringList) {
                    formatterManager.selectFormatter(dto[it])
                }
                router.showDialog(dialog)
            }.subscribe()
    }

    private fun getCurrentFullDateFormat() {
        formatterManager.getSelectedFormatter()
            .observeUi()
            .doAfterSuccess {
                val date = LocalDate.now()
                currentFullDateFormatter.value = date.format(it.formatter)
            }.subscribe()
    }

    override fun onTriggerEvent(eventType: SettingsScreenIntent) {
        when (eventType) {
            SettingsScreenIntent.SetFullDateFormatter -> getFullDateTimeFormatters()
            SettingsScreenIntent.GetCurrentDateFormatter -> getCurrentFullDateFormat()
        }
    }
}
