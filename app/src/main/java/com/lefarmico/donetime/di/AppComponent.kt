package com.lefarmico.donetime.di

import com.lefarmico.presentation.viewModels.AddExerciseViewModel
import com.lefarmico.presentation.viewModels.CategoryListViewModel
import com.lefarmico.presentation.viewModels.ExerciseDetailsViewModel
import com.lefarmico.presentation.viewModels.ExerciseListViewModel
import com.lefarmico.presentation.viewModels.HomeViewModel
import com.lefarmico.presentation.viewModels.SubCategoryViewModel
import com.lefarmico.presentation.viewModels.WorkoutScreenViewModel
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
    fun inject(viewModel: CategoryListViewModel)
    fun inject(viewModel: SubCategoryViewModel)
    fun inject(viewModel: WorkoutScreenViewModel)
    fun inject(viewModel: HomeViewModel)
    fun inject(viewModel: AddExerciseViewModel)
    fun inject(viewModel: ExerciseDetailsViewModel)
}
