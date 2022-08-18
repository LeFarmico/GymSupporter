package com.lefarmico.data.di

import com.lefarmico.data.preference.*
import com.lefarmico.domain.preferences.*
import dagger.Binds
import dagger.Module
import javax.inject.Singleton

@Module
abstract class PreferenceModule {

    @Binds
    @Singleton //  Может не Singleton, может отдельный компонент ???
    abstract fun provideFormatterPreferenceHelper(
        helperImpl: FormatterPreferenceHelperImpl
    ): FormatterPreferenceHelper

    @Binds
    @Singleton //  Может не Singleton, может отдельный компонент ???
    abstract fun provideFormatterMonthPreferenceHelper(
        helperImpl: FormatterMonthPreferenceHelperImpl
    ): FormatterMonthPreferenceHelper

    @Binds
    @Singleton //  Может не Singleton, может отдельный компонент ???
    abstract fun provideFormatterTimePreferenceHelper(
        helperImpl: FormatterTimePreferenceHelperImpl
    ): FormatterTimePreferenceHelper

    @Binds
    @Singleton //  Может не Singleton, может отдельный компонент ???
    abstract fun provideRemindTimePreferenceHelper(
        helperImpl: RemindPreferenceHelperImpl
    ): RemindPreferenceHelper

    @Binds
    @Singleton
    abstract fun provideFirstTimePreferenceHelper(
        helperImpl: FirstLaunchPreferenceHelperImpl
    ): FirstLaunchPreferenceHelper

    @Binds
    @Singleton
    abstract fun provideThemePreferenceHelper(
        helperImpl: ThemeSettingsPreferenceHelperImpl
    ): ThemeSettingsPreferenceHelper
}
