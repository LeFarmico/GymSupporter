package com.lefarmico.donetime

import android.app.Application
import com.lefarmico.donetime.di.AppComponent
import com.lefarmico.donetime.di.DaggerAppComponent
import com.lefarmico.presentation.di.PresentationComponent
import com.lefarmico.presentation.di.modules.DataBaseModule
import com.lefarmico.presentation.di.modules.DomainModule
import com.lefarmico.presentation.di.viewModel.ViewModelModule

class App : Application() {

    companion object {
        lateinit var appComponent: AppComponent
            private set

        lateinit var presentationComponent: PresentationComponent
            private set
    }

    override fun onCreate() {
        super.onCreate()

        appComponent = DaggerAppComponent.builder().application(this).build()
        presentationComponent = appComponent.presentationComponent(
            DataBaseModule(),
            DomainModule(this)
        )
    }
}
