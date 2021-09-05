package com.lefarmico.donetime.views.fragments.listMenu

import android.os.Bundle
import com.lefarmico.donetime.databinding.FragmentExerciseDetailsBinding
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
        viewModel.getExerciseFromDB(bundleResult)
    }

    override fun observeData() {
        viewModel.libraryExerciseLiveData.observe(viewLifecycleOwner) {
            binding.exerciseTitleTextView.text = it.title
            binding.exerciseDescriptionTextView.text = it.description
        }
    }

    private fun getBundleResult() {
        val bundle = this.arguments
        if (bundle != null) {
            bundleResult = bundle.getInt(ExerciseListFragment.KEY_NUMBER)
        }
    }
}
