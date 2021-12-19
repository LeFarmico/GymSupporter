package com.lefarmico.data.di

import com.lefarmico.data.preference.FormatterPreferenceHelperImpl
import com.lefarmico.domain.preferences.FormatterPreferenceHelper
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
}
