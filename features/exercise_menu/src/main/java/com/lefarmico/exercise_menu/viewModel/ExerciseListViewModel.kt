package com.lefarmico.exercise_menu.viewModel

import androidx.lifecycle.MutableLiveData
import com.lefarmico.core.base.BaseViewModel
import com.lefarmico.core.entity.LibraryViewData
import com.lefarmico.core.mapper.toViewDataExercise
import com.lefarmico.domain.repository.LibraryRepository
import com.lefarmico.domain.utils.DataState
import com.lefarmico.exercise_menu.intent.ExerciseListIntent
import com.lefarmico.navigation.Router
import com.lefarmico.navigation.params.LibraryParams
import com.lefarmico.navigation.params.WorkoutScreenParams
import com.lefarmico.navigation.screen.Screen
import javax.inject.Inject

class ExerciseListViewModel @Inject constructor() : BaseViewModel<ExerciseListIntent>() {

    @Inject
    lateinit var repo: LibraryRepository
    @Inject
    lateinit var router: Router

    val exercisesLiveData = MutableLiveData<DataState<List<LibraryViewData.Exercise>>>()

    private fun getExercises(subCategoryId: Int) {
        repo.getExercises(subCategoryId)
            .subscribe { dataState ->
                when (dataState) {
                    is DataState.Success -> {
                        val success = DataState.Success(dataState.data.toViewDataExercise())
                        exercisesLiveData.postValue(success)
                    }
                    else -> {
                        exercisesLiveData.postValue(
                            dataState as DataState<List<LibraryViewData.Exercise>>
                        )
                    }
                }
            }
    }

    private fun cleanAll() {
        exercisesLiveData.postValue(DataState.Empty)
    }

    private fun addExerciseToWorkout(exerciseId: Int) {
        router.navigate(
            Screen.ACTION_ADD_EXERCISE_TO_WORKOUT_SCREEN,
            WorkoutScreenParams.NewExercise(exerciseId)
        )
    }

    private fun goToExerciseDetailsScreen(exerciseLibraryId: Int) {
        router.navigate(
            screen = Screen.EXERCISE_DETAILS_SCREEN,
            data = LibraryParams.Exercise(exerciseLibraryId)
        )
    }

    private fun createNewExercise(
        categoryId: Int,
        subCategoryId: Int,
        isFromWorkoutScreen: Boolean
    ) {
        router.navigate(
            screen = Screen.CREATE_NEW_EXERCISE_SCREEN,
            data = LibraryParams.NewExercise(categoryId, subCategoryId, isFromWorkoutScreen)
        )
    }

    override fun onTriggerEvent(eventType: ExerciseListIntent) {
        when (eventType) {
            is ExerciseListIntent.GetExercises -> getExercises(eventType.subcategoryId)
            ExerciseListIntent.CleanAll -> cleanAll()
            is ExerciseListIntent.AddExerciseToWorkoutScreen -> addExerciseToWorkout(
                eventType.exerciseId
            )
            is ExerciseListIntent.GoToExerciseDetailsScreen -> goToExerciseDetailsScreen(
                eventType.exerciseId
            )
            is ExerciseListIntent.CreateNewExercise -> createNewExercise(
                eventType.categoryId,
                eventType.categoryId,
                eventType.isFromWorkoutScreen
            )
        }
    }
}
