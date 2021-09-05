package com.lefarmico.donetime.views.fragments.listMenu

import android.os.Bundle
import com.lefarmico.domain.utils.DataState
import com.lefarmico.donetime.databinding.FragmentExerciseDetailsBinding
import com.lefarmico.donetime.intents.ExerciseDetailsIntent
import com.lefarmico.donetime.viewModels.ExerciseDetailsViewModel
import com.lefarmico.donetime.views.base.BaseFragment

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
                DataState.Empty -> {}
                is DataState.Error -> {}
                DataState.Loading -> {}
                is DataState.Success -> {
                    binding.exerciseTitleTextView.text = dataState.data.title
                    binding.exerciseDescriptionTextView.text = dataState.data.description
                }
            }
        }
    }

    private fun getBundleResult() {
        val bundle = this.arguments
        if (bundle != null) {
            bundleResult = bundle.getInt(ExerciseListFragment.KEY_NUMBER)
        }
    }
}
