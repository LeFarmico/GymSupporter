package com.lefarmico.presentation.di.modules

import com.lefarmico.presentation.di.scopes.FragmentScope
import com.lefarmico.presentation.views.fragments.AddExerciseFragment
import com.lefarmico.presentation.views.fragments.HomeFragment
import com.lefarmico.presentation.views.fragments.WorkoutScreenFragment
import com.lefarmico.presentation.views.fragments.listMenu.ExerciseDetailsFragment
import com.lefarmico.presentation.views.fragments.listMenu.library.LibraryCategoryFragment
import com.lefarmico.presentation.views.fragments.listMenu.library.LibraryExerciseFragment
import com.lefarmico.presentation.views.fragments.listMenu.library.LibrarySubCategoryFragment
import com.lefarmico.presentation.views.fragments.listMenu.workout.WorkoutCategoryFragment
import com.lefarmico.presentation.views.fragments.listMenu.workout.WorkoutExerciseFragment
import com.lefarmico.presentation.views.fragments.listMenu.workout.WorkoutSubcategoryFragment
import dagger.Module
import dagger.android.ContributesAndroidInjector

@Module
abstract class FragmentBindingModule {
    
    @FragmentScope
    @ContributesAndroidInjector
    abstract fun provideHomeFragment(): HomeFragment

    @FragmentScope
    @ContributesAndroidInjector
    abstract fun provideAddExerciseFragment(): AddExerciseFragment

    @FragmentScope
    @ContributesAndroidInjector
    abstract fun provideWorkoutScreenFragment(): WorkoutScreenFragment

    @FragmentScope
    @ContributesAndroidInjector
    abstract fun provideExerciseDetailsFragment(): ExerciseDetailsFragment

    @FragmentScope
    @ContributesAndroidInjector
    abstract fun provideLibraryCategoryFragment(): LibraryCategoryFragment

    @FragmentScope
    @ContributesAndroidInjector
    abstract fun provideLibraryExerciseFragment(): LibraryExerciseFragment

    @FragmentScope
    @ContributesAndroidInjector
    abstract fun provideLibrarySubCategoryFragment(): LibrarySubCategoryFragment

    @FragmentScope
    @ContributesAndroidInjector
    abstract fun provideWorkoutCategoryFragment(): WorkoutCategoryFragment

    @FragmentScope
    @ContributesAndroidInjector
    abstract fun provideWorkoutExerciseFragment(): WorkoutExerciseFragment

    @FragmentScope
    @ContributesAndroidInjector
    abstract fun provideWorkoutSubcategoryFragment(): WorkoutSubcategoryFragment
}
