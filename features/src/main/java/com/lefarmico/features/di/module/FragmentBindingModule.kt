package com.lefarmico.features.di.module

import com.lefarmico.create_new_exercise.CreateExerciseFragment
import com.lefarmico.detailed_record_workout.DetailedWorkoutRecordFragment
import com.lefarmico.exercise_menu.view.CategoryFragment
import com.lefarmico.exercise_menu.view.ExerciseListFragment
import com.lefarmico.exercise_menu.view.SubcategoryFragment
import com.lefarmico.features.di.FragmentScope
import com.lefarmico.home.HomeFragment
import com.lefarmico.settings_screen.SettingsScreenFragment
import com.lefarmico.workout.WorkoutFragment
import com.lefarmico.workout_exercise_addition.ExerciseDetailsFragment
import dagger.Module
import dagger.android.ContributesAndroidInjector

@Module
abstract class FragmentBindingModule {

    @FragmentScope
    @ContributesAndroidInjector
    abstract fun provideHomeFragment(): HomeFragment

    @FragmentScope
    @ContributesAndroidInjector
    abstract fun provideAddExerciseFragment(): CreateExerciseFragment

    @FragmentScope
    @ContributesAndroidInjector
    abstract fun provideWorkoutScreenFragment(): WorkoutFragment

    @FragmentScope
    @ContributesAndroidInjector
    abstract fun provideExerciseDetailsFragment(): ExerciseDetailsFragment

    @FragmentScope
    @ContributesAndroidInjector
    abstract fun provideWorkoutCategoryFragment(): CategoryFragment

    @FragmentScope
    @ContributesAndroidInjector
    abstract fun provideWorkoutExerciseFragment(): SubcategoryFragment

    @FragmentScope
    @ContributesAndroidInjector
    abstract fun provideWorkoutSubcategoryFragment(): ExerciseListFragment

    @FragmentScope
    @ContributesAndroidInjector
    abstract fun provideEditWorkoutRecordFragment(): DetailedWorkoutRecordFragment

    @FragmentScope
    @ContributesAndroidInjector
    abstract fun provideSettingsMenuFragment(): SettingsScreenFragment
}
