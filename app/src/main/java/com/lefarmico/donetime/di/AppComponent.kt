package com.lefarmico.donetime.di

import com.lefarmico.donetime.viewModels.ExerciseListViewModel
import dagger.Component
import javax.inject.Singleton

@Component(
    modules = [
        DataBaseModule::class,
        DomainModule::class
    ]
)
@Singleton
interface AppComponent {
    // ViewModels
    fun inject(viewModel: ExerciseListViewModel)
}
