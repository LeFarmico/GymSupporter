package com.lefarmico.workout_exercise_addition.view

import android.os.Bundle
import android.os.Parcelable
import com.lefarmico.core.BuildConfig
import com.lefarmico.core.base.BaseFragment
import com.lefarmico.domain.utils.DataState
import com.lefarmico.navigation.params.LibraryParams
import com.lefarmico.workout_exercise_addition.databinding.FragmentExerciseDetailsBinding
import com.lefarmico.workout_exercise_addition.intent.ExerciseDetailsIntent
import com.lefarmico.workout_exercise_addition.viewModel.ExerciseDetailsViewModel
import java.lang.IllegalArgumentException

class ExerciseDetailsFragment : BaseFragment<FragmentExerciseDetailsBinding, ExerciseDetailsViewModel>(
    FragmentExerciseDetailsBinding::inflate,
    ExerciseDetailsViewModel::class.java
) {

    private var bundleResult = -1

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        getBundleResult()
    }

    override fun setUpViews() {
    }

    override fun observeView() {
        viewModel.onTriggerEvent(
            ExerciseDetailsIntent.GetExercise(
                bundleResult
            )
        )
    }

    override fun observeData() {
        viewModel.libraryExerciseLiveData.observe(viewLifecycleOwner) { dataState ->
            when (dataState) {
                DataState.Empty -> {
                    binding.state.showEmptyState()
                }
                is DataState.Error -> {
                    binding.state.showErrorState()
                }
                DataState.Loading -> {
                    binding.state.showLoadingState()
                }
                is DataState.Success -> {
                    binding.state.showSuccessState()
                    binding.exerciseTitleTextView.text = dataState.data.title
                    binding.exerciseDescriptionTextView.text = dataState.data.description
                }
            }
        }
    }

    private fun getBundleResult() {
        val bundle = this.arguments
        if (bundle != null) {
            // TODO navigation module
//            bundleResult = bundle.getInt(com.lefarmico.exercise_menu.view.ExerciseListFragment.KEY_NUMBER)
        }
    }
    companion object {
        private const val KEY_PARAMS = "exercise_details_key"

        fun createBundle(data: Parcelable?): Bundle {
            return Bundle().apply {
                when (data) {
                    is LibraryParams.ExerciseList -> putParcelable(KEY_PARAMS, data)
                    else -> {
                        if (BuildConfig.DEBUG) {
                            throw (
                                IllegalArgumentException(
                                    "data should be LibraryParams.Exercise type."
                                )
                                )
                        }
                    }
                }
            }
        }
    }
}
