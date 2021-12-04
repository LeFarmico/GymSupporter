package com.lefarmico.features.di.module

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.lefarmico.core.di.ViewModelFactory
import com.lefarmico.create_new_exercise.viewModel.CreateNewExerciseViewModel
import com.lefarmico.detailed_record_workout.viewModel.DetailedWorkoutRecordViewModel
import com.lefarmico.exercise_menu.viewModel.CategoryListViewModel
import com.lefarmico.exercise_menu.viewModel.ExerciseListViewModel
import com.lefarmico.exercise_menu.viewModel.SubCategoryViewModel
import com.lefarmico.features.di.ViewModelKey
import com.lefarmico.home.viewModel.HomeViewModel
import com.lefarmico.workout.viewModel.WorkoutScreenViewModel
import com.lefarmico.workout_exercise_addition.viewModel.ExerciseDetailsViewModel
import dagger.Binds
import dagger.Module
import dagger.multibindings.IntoMap

@Module
abstract class ViewModelModule {

    @Binds
    abstract fun bindViewModelFactory(factory: ViewModelFactory): ViewModelProvider.Factory

    @Binds
    @IntoMap
    @ViewModelKey(CategoryListViewModel::class)
    abstract fun addCategoryListViewModel(viewModel: CategoryListViewModel): ViewModel

    @Binds
    @IntoMap
    @ViewModelKey(CreateNewExerciseViewModel::class)
    abstract fun addAddExerciseViewModel(viewModel: CreateNewExerciseViewModel): ViewModel

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

    @Binds
    @IntoMap
    @ViewModelKey(DetailedWorkoutRecordViewModel::class)
    abstract fun addModelEditWorkoutRecordViewModel(viewModel: DetailedWorkoutRecordViewModel): ViewModel
}
