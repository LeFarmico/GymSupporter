package com.lefarmico.features.di.module

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.lefarmico.core.di.ViewModelFactory
import com.lefarmico.create_new_exercise.CreateExerciseViewModel
import com.lefarmico.detailed_record_workout.DetailedWorkoutRecordViewModel
import com.lefarmico.exercise_menu.viewModel.CategoryViewModel
import com.lefarmico.exercise_menu.viewModel.ExerciseListViewModel
import com.lefarmico.exercise_menu.viewModel.SubcategoryViewModel
import com.lefarmico.features.di.ViewModelKey
import com.lefarmico.home.HomeViewModel
import com.lefarmico.settings_screen.SettingsScreenViewModel
import com.lefarmico.workout.WorkoutViewModel
import com.lefarmico.workout_exercise_addition.ExerciseDetailsViewModel
import dagger.Binds
import dagger.Module
import dagger.multibindings.IntoMap

@Module
abstract class ViewModelModule {

    @Binds
    abstract fun bindViewModelFactory(factory: ViewModelFactory): ViewModelProvider.Factory

    @Binds
    @IntoMap
    @ViewModelKey(CategoryViewModel::class)
    abstract fun addCategoryListViewModel(viewModel: CategoryViewModel): ViewModel

    @Binds
    @IntoMap
    @ViewModelKey(CreateExerciseViewModel::class)
    abstract fun addAddExerciseViewModel(viewModel: CreateExerciseViewModel): ViewModel

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
    @ViewModelKey(SubcategoryViewModel::class)
    abstract fun addSubCategoryViewModel(viewModel: SubcategoryViewModel): ViewModel

    @Binds
    @IntoMap
    @ViewModelKey(WorkoutViewModel::class)
    abstract fun addWorkoutScreenViewModel(viewModel: WorkoutViewModel): ViewModel

    @Binds
    @IntoMap
    @ViewModelKey(DetailedWorkoutRecordViewModel::class)
    abstract fun addModelEditWorkoutRecordViewModel(viewModel: DetailedWorkoutRecordViewModel): ViewModel

    @Binds
    @IntoMap
    @ViewModelKey(SettingsScreenViewModel::class)
    abstract fun addSettingsScreenViewModel(viewModel: SettingsScreenViewModel): ViewModel
}
