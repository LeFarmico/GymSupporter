package com.lefarmico.donetime.views.fragments.listMenu

import android.os.Bundle
import androidx.core.os.bundleOf
import androidx.fragment.app.Fragment
import com.lefarmico.domain.entity.LibraryDto
import com.lefarmico.domain.utils.DataState
import com.lefarmico.donetime.R
import com.lefarmico.donetime.adapters.ExerciseLibraryAdapter
import com.lefarmico.donetime.databinding.FragmentExerciseListBinding
import com.lefarmico.donetime.intents.ExerciseListIntent
import com.lefarmico.donetime.viewModels.ExerciseListViewModel
import com.lefarmico.donetime.views.base.BaseFragment
import com.lefarmico.donetime.views.fragments.AddExerciseFragment
import com.lefarmico.donetime.views.fragments.WorkoutScreenFragment

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
                    adapter.items = mutableListOf()
                }
                is DataState.Error -> {}
                DataState.Loading -> {}
                is DataState.Success -> {
                    adapter.items = dataState.data
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
