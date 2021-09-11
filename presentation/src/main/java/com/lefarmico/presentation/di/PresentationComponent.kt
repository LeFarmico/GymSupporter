package com.lefarmico.presentation.di

import com.lefarmico.presentation.di.modules.DataBaseModule
import com.lefarmico.presentation.di.modules.DomainModule
import com.lefarmico.presentation.di.scopes.PresentationScope
import com.lefarmico.presentation.di.viewModel.ViewModelModule
import dagger.Subcomponent

@PresentationScope
@Subcomponent(
    modules = [
        DataBaseModule::class,
        DomainModule::class,
        ViewModelModule::class
    ]
)
interface PresentationComponent
