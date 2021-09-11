package com.lefarmico.presentation.di.viewModel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.lefarmico.presentation.viewModels.* // ktlint-disable no-wildcard-imports
import dagger.Binds
import dagger.Module
import dagger.multibindings.IntoMap

@Module
abstract class ViewModelModule {

    @Binds
    abstract fun bindViewModelFactory(factory: ViewModelFactory): ViewModelProvider.Factory

    @Binds
    @IntoMap
    @ViewModelKey(AddExerciseViewModel::class)
    abstract fun addAddExerciseViewModel(viewModel: AddExerciseViewModel): ViewModel

    @Binds
    @IntoMap
    @ViewModelKey(CategoryListViewModel::class)
    abstract fun addCategoryListViewModel(viewModel: CategoryListViewModel): ViewModel

    @Binds
    @IntoMap
    @ViewModelKey(ExerciseDetailsViewModel::class)
    abstract fun addExerciseDetailsViewModel(viewModel: ExerciseDetailsViewModel): ViewModel

    @Binds
    @IntoMap
    @ViewModelKey(ExerciseListViewModel::class)
    abstract fun addExerciseListViewModel(viewModel: ExerciseListViewModel): ViewModel

    @Binds
    @IntoMap
    @ViewModelKey(HomeViewModel::class)
    abstract fun addHomeViewModel(viewModel: HomeViewModel): ViewModel

    @Binds
    @IntoMap
    @ViewModelKey(SubCategoryViewModel::class)
    abstract fun addSubCategoryViewModel(viewModel: SubCategoryViewModel): ViewModel

    @Binds
    @IntoMap
    @ViewModelKey(WorkoutScreenViewModel::class)
    abstract fun addWorkoutScreenViewModel(viewModel: WorkoutScreenViewModel): ViewModel
}
