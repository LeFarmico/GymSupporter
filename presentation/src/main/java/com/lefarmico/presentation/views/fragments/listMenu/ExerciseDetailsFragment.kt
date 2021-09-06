package com.lefarmico.presentation.views.fragments.listMenu

import android.os.Bundle
import android.view.View
import com.lefarmico.domain.utils.DataState
import com.lefarmico.presentation.databinding.FragmentExerciseDetailsBinding
import com.lefarmico.presentation.intents.ExerciseDetailsIntent
import com.lefarmico.presentation.viewModels.ExerciseDetailsViewModel
import com.lefarmico.presentation.views.base.BaseFragment

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
                    binding.emptyState.root.visibility = View.VISIBLE
                    binding.errorState.root.visibility = View.GONE
                    binding.loadingState.root.visibility = View.GONE
                }
                is DataState.Error -> {
                    binding.errorState.root.visibility = View.VISIBLE
                    binding.emptyState.root.visibility = View.GONE
                    binding.loadingState.root.visibility = View.GONE
                }
                DataState.Loading -> {
                    binding.loadingState.root.visibility = View.VISIBLE
                    binding.emptyState.root.visibility = View.GONE
                    binding.errorState.root.visibility = View.GONE
                }
                is DataState.Success -> {
                    binding.exerciseTitleTextView.text = dataState.data.title
                    binding.exerciseDescriptionTextView.text = dataState.data.description
                    binding.emptyState.root.visibility = View.GONE
                    binding.errorState.root.visibility = View.GONE
                    binding.loadingState.root.visibility = View.GONE
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
