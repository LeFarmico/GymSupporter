package com.lefarmico.presentation.di

import com.lefarmico.presentation.di.modules.DataBaseModule
import com.lefarmico.presentation.di.modules.DomainModule
import com.lefarmico.presentation.di.scopes.PresentationScope
import com.lefarmico.presentation.viewModels.* // ktlint-disable no-wildcard-imports
import dagger.Component

@PresentationScope
@Component(
    modules = [
        DataBaseModule::class,
        DomainModule::class
    ]
)
interface PresentationComponent {
    // ViewModels
    fun inject(viewModel: ExerciseListViewModel)
    fun inject(viewModel: CategoryListViewModel)
    fun inject(viewModel: SubCategoryViewModel)
    fun inject(viewModel: WorkoutScreenViewModel)
    fun inject(viewModel: HomeViewModel)
    fun inject(viewModel: AddExerciseViewModel)
    fun inject(viewModel: ExerciseDetailsViewModel)
}
