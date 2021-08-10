package com.lefarmico.donetime.views.fragments

import android.os.Bundle
import androidx.core.os.bundleOf
import androidx.fragment.app.FragmentManager
import com.lefarmico.donetime.adapters.ExerciseListAdapter
import com.lefarmico.donetime.data.entities.library.ItemLibraryExercise
import com.lefarmico.donetime.data.entities.traning.exercise.ExerciseNameEntity
import com.lefarmico.donetime.databinding.FragmentExerciseListBinding
import com.lefarmico.donetime.viewModels.ExerciseListViewModel
import com.lefarmico.donetime.views.base.BaseFragment

class ExerciseListFragment : BaseFragment<FragmentExerciseListBinding, ExerciseListViewModel>(
    FragmentExerciseListBinding::inflate,
    ExerciseListViewModel::class.java
) {

    var bundleResult: Int = -1

    private val adapter = ExerciseListAdapter().apply {
        setOnClickEvent {
            it as ItemLibraryExercise
            setResult(ExerciseNameEntity(it.title, it.title))
            parentFragmentManager.popBackStack(
                WorkoutScreenFragment.BACKSTACK_BRANCH,
                FragmentManager.POP_BACK_STACK_INCLUSIVE
            )
        }
    }
    
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        getBundleResult()
    }
    override fun setUpViews() {
        binding.recycler.adapter = adapter
        viewModel.passExercisesToLiveData(bundleResult)
    }

    override fun observeData() {
        viewModel.exercisesLiveData.observe(viewLifecycleOwner) { list ->
            adapter.setExercises(list)
        }
    }

    private fun setResult(exerciseEntity: ExerciseNameEntity) {
        parentFragmentManager.setFragmentResult(
            WorkoutScreenFragment.REQUEST_KEY,
            bundleOf(WorkoutScreenFragment.KEY_NUMBER to exerciseEntity)
        )
    }

    private fun getBundleResult() {
        val bundle = this.arguments
        if (bundle != null) {
            bundleResult = bundle.getInt(SubCategoryListFragment.KEY_NUMBER)
        }
    }
}
