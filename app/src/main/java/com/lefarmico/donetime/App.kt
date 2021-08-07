package com.lefarmico.donetime

import android.app.Application
import com.lefarmico.donetime.di.AppComponent
import com.lefarmico.donetime.di.DaggerAppComponent
import com.lefarmico.donetime.di.DataBaseModel

class App : Application() {

    companion object {
        lateinit var appComponent: AppComponent
            private set
    }

    override fun onCreate() {
        super.onCreate()
        appComponent = DaggerAppComponent.builder()
            .dataBaseModel(DataBaseModel(this))
            .build()
    }
}
