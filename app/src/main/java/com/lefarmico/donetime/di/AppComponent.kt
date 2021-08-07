package com.lefarmico.donetime.di

import com.lefarmico.donetime.viewModels.ExerciseListViewModel
import dagger.Component
import javax.inject.Singleton

@Component(
    modules = [
        DataBaseModel::class
    ]
)
@Singleton
interface AppComponent {
    // ViewModels
    fun inject(viewModel: ExerciseListViewModel)
}
