package com.lefarmico.presentation.views.fragments.listMenu

import android.os.Bundle
import android.view.View
import androidx.core.os.bundleOf
import androidx.fragment.app.Fragment
import com.lefarmico.domain.entity.LibraryDto
import com.lefarmico.domain.utils.DataState
import com.lefarmico.presentation.R
import com.lefarmico.presentation.adapters.ExerciseLibraryAdapter
import com.lefarmico.presentation.databinding.FragmentExerciseListBinding
import com.lefarmico.presentation.intents.ExerciseListIntent
import com.lefarmico.presentation.viewModels.ExerciseListViewModel
import com.lefarmico.presentation.views.base.BaseFragment
import com.lefarmico.presentation.views.fragments.AddExerciseFragment
import com.lefarmico.presentation.views.fragments.WorkoutScreenFragment

abstract class ExerciseListFragment : BaseFragment<FragmentExerciseListBinding, ExerciseListViewModel>(
    FragmentExerciseListBinding::inflate,
    ExerciseListViewModel::class.java
) {

    abstract val onItemClickListener: (LibraryDto) -> Unit
    
    var bundleResult: Int = -1
    private val bundle = Bundle()

    private val adapter = ExerciseLibraryAdapter()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        getBundleResult()
    }

    override fun setUpViews() {
        adapter.onClick = onItemClickListener
        binding.recycler.adapter = adapter
        viewModel.onTriggerEvent(
            ExerciseListIntent.GetExercises(bundleResult)
        )

        binding.plusButton.setOnClickListener {
            bundle.putInt(AddExerciseFragment.KEY_NUMBER, bundleResult)
            changeFragment(AddExerciseFragment::class.java, bundle)
        }
    }

    override fun observeData() {
        viewModel.exercisesLiveData.observe(viewLifecycleOwner) { dataState ->
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
                    adapter.items = dataState.data
                    binding.emptyState.root.visibility = View.GONE
                    binding.errorState.root.visibility = View.GONE
                    binding.loadingState.root.visibility = View.GONE
                }
            }
        }
    }

    protected fun setExerciseResult(exerciseId: Int) {
        parentFragmentManager.setFragmentResult(
            WorkoutScreenFragment.REQUEST_KEY,
            bundleOf(WorkoutScreenFragment.KEY_NUMBER to exerciseId)
        )
    }

    private fun getBundleResult() {
        val bundle = this.arguments
        if (bundle != null) {
            bundleResult = bundle.getInt(SubCategoryListFragment.KEY_NUMBER)
        }
    }

    private fun changeFragment(
        fragment: Class<out Fragment>,
        bundle: Bundle
    ) {
        parentFragmentManager.beginTransaction()
            .replace(R.id.fragment, fragment, bundle)
            .addToBackStack(AddExerciseFragment.BACK_STACK_KEY)
            .commit()
    }

    companion object {
        const val KEY_NUMBER = "exercise_list_fragment_key"
    }
}
