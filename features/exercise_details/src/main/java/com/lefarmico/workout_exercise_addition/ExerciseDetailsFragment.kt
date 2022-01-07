package com.lefarmico.workout_exercise_addition

import android.os.Bundle
import android.os.Parcelable
import com.lefarmico.core.BuildConfig
import com.lefarmico.core.base.BaseFragment
import com.lefarmico.core.entity.LibraryViewData
import com.lefarmico.navigation.params.LibraryParams
import com.lefarmico.workout_exercise_addition.databinding.FragmentExerciseDetailsBinding

class ExerciseDetailsFragment : BaseFragment<
    ExerciseDetailsIntent, ExerciseDetailsAction, ExerciseDetailsState, ExerciseDetailsEvent,
    FragmentExerciseDetailsBinding, ExerciseDetailsViewModel>(
    FragmentExerciseDetailsBinding::inflate,
    ExerciseDetailsViewModel::class.java
) {

    private val params: LibraryParams.Exercise by lazy {
        arguments?.getParcelable<LibraryParams.Exercise>(KEY_PARAMS)
            ?: throw (IllegalArgumentException("Arguments params must be not null"))
    }

    override fun observeView() {
        val exerciseId = params.exerciseId
        dispatchIntent(ExerciseDetailsIntent.GetExercise(exerciseId))
    }

    private fun showExerciseDetails(libraryExercise: LibraryViewData.Exercise) {
        binding.exerciseTitleTextView.text = libraryExercise.title
        binding.exerciseDescriptionTextView.text = libraryExercise.description
    }

    override fun receive(state: ExerciseDetailsState) {
        when (state) {
            is ExerciseDetailsState.ExceptionResult -> throw (state.exception)
            is ExerciseDetailsState.ExerciseResult -> showExerciseDetails(state.exercise)
            ExerciseDetailsState.Loading -> {}
        }
    }

    override fun receive(event: ExerciseDetailsEvent) {}

    companion object {
        private const val KEY_PARAMS = "exercise_details_key"

        fun createBundle(data: Parcelable?): Bundle {
            return Bundle().apply {
                when (data) {
                    is LibraryParams.Exercise -> putParcelable(KEY_PARAMS, data)
                    else -> {
                        if (BuildConfig.DEBUG) {
                            throw (
                                IllegalArgumentException(
                                    "data should be LibraryParams.Exercise type." +
                                        "but it's ${data!!.javaClass.simpleName}"
                                )
                                )
                        }
                    }
                }
            }
        }
    }
}
