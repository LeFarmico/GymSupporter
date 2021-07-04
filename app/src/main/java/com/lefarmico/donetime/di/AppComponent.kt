package com.lefarmico.donetime.di

import com.lefarmico.donetime.ui.base.BaseViewModel
import dagger.Component

@Component
interface AppComponent {
    // ViewModels
    fun inject(viewModel: BaseViewModel)
}
