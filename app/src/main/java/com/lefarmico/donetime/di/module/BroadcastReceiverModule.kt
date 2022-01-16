package com.lefarmico.donetime.di.module

import com.lefarmico.workout_notification.RemindReceiver
import dagger.Module
import dagger.android.ContributesAndroidInjector

@Module
abstract class BroadcastReceiverModule {

    @ContributesAndroidInjector
    abstract fun provideRemindReceiver(): RemindReceiver
}
