package com.lefarmico.donetime.views.fragments

import android.os.Bundle
import androidx.fragment.app.replace
import com.lefarmico.donetime.R
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
        val bundle = Bundle()
        viewModel.exercisesLiveData.observe(viewLifecycleOwner) { list ->
            adapter.setExercises(list)
        }
        adapter.setOnClickEvent {
            it as ItemLibraryExercise
            bundle.putParcelable("Exercise", ExerciseNameEntity(it.title, it.title))
            parentFragmentManager.beginTransaction()
                .replace<WorkoutScreenFragment>(R.id.fragment, "Workout", bundle)
//                .replace(
//
//                    WorkoutScreenFragment().apply { arguments = bundle },
//                    "Workout"
//                )
                .disallowAddToBackStack()
                .commit()
        }
        binding.recycler.adapter = adapter
    }
}
