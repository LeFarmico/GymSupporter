package com.lefarmico.donetime.view

import com.lefarmico.core.base.BaseViewModel
import com.lefarmico.core.extensions.observeUi
import com.lefarmico.domain.loaders.MuscleCategoryLoader
import com.lefarmico.domain.preferences.FirstLaunchPreferenceHelper
import com.lefarmico.navigation.Router
import com.lefarmico.navigation.notification.Notification
import com.lefarmico.navigation.params.ToastBarParams
import javax.inject.Inject

class MainViewModel @Inject constructor(
    private val firstLaunchPreferenceHelper: FirstLaunchPreferenceHelper,
    private val muscleCategoryLoader: MuscleCategoryLoader,
    private val router: Router
) : BaseViewModel<MainIntent, MainState, MainEvent>() {

    private fun loadPreloadedData() {
        firstLaunchPreferenceHelper.onOnFirstLaunchListener {
            muscleCategoryLoader.loadMuscleCategory()
                .observeUi()
                .doOnError { mEvent.postValue(MainEvent.LoadDataResult.Failure) }
                .doAfterSuccess { mEvent.postValue(MainEvent.LoadDataResult.Failure) }
                .subscribe()
        }
    }

    private fun showToast(text: String) {
        router.show(Notification.TOAST, ToastBarParams(text))
    }

    override fun triggerIntent(intent: MainIntent) {
        when (intent) {
            MainIntent.LoadPreloadedData -> loadPreloadedData()
            is MainIntent.ShowToast -> showToast(intent.text)
        }
    }
}
