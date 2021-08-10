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
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val bundle = this.arguments
        if (bundle != null) {
            viewModel.passExercisesToLiveData(
                bundle.getInt("SubCategory")
            )
        }
    }
    override fun setUpViews() {
        val adapter = ExerciseListAdapter()
        viewModel.exercisesLiveData.observe(viewLifecycleOwner) { list ->
            adapter.setExercises(list)
        }
        adapter.setOnClickEvent {
            it as ItemLibraryExercise
            parentFragmentManager.setFragmentResult(
                WorkoutScreenFragment.REQUEST_KEY,
                bundleOf(WorkoutScreenFragment.KEY_NUMBER to ExerciseNameEntity(it.title, it.title))
            )
            parentFragmentManager.popBackStack("EX", FragmentManager.POP_BACK_STACK_INCLUSIVE)
        }
        binding.recycler.adapter = adapter
    }
}
