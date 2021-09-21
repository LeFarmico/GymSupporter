package com.lefarmico.exercise_menu.view

import android.os.Bundle
import android.os.Parcelable
import androidx.fragment.app.Fragment
import com.lefarmico.core.BuildConfig
import com.lefarmico.core.adapter.ExerciseLibraryAdapter
import com.lefarmico.core.base.BaseFragment
import com.lefarmico.domain.entity.LibraryDto
import com.lefarmico.domain.utils.DataState
import com.lefarmico.exercise_menu.databinding.FragmentExerciseListBinding
import com.lefarmico.exercise_menu.intent.ExerciseListIntent
import com.lefarmico.exercise_menu.viewModel.ExerciseListViewModel
import com.lefarmico.navigation.params.LibraryParams
import java.lang.IllegalArgumentException

abstract class ExerciseListFragment : BaseFragment<FragmentExerciseListBinding, ExerciseListViewModel>(
    FragmentExerciseListBinding::inflate,
    ExerciseListViewModel::class.java
) {

    abstract val onItemClickListener: (LibraryDto) -> Unit

    private var bundleResult: Int = -1
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
            // TODO navigation module
//            bundle.putInt(AddExerciseFragment.KEY_NUMBER, bundleResult)
//            changeFragment(AddExerciseFragment::class.java, bundle)
        }
    }

    override fun observeData() {
        viewModel.exercisesLiveData.observe(viewLifecycleOwner) { dataState ->
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
                    adapter.items = dataState.data
                    binding.state.showSuccessState()
                }
            }
        }
    }

    protected fun setExerciseResult(exerciseId: Int) {
        // TODO navigation module
//        parentFragmentManager.setFragmentResult(
//            com.lefarmico.workout.view.WorkoutScreenFragment.REQUEST_KEY,
//            bundleOf(com.lefarmico.workout.view.WorkoutScreenFragment.KEY_NUMBER to exerciseId)
//        )
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
        // TODO navigation module
//        parentFragmentManager.beginTransaction()
//            .replace(R.id.fragment, fragment, bundle)
//            .addToBackStack(AddExerciseFragment.BACK_STACK_KEY)
//            .commit()
    }

    companion object {
        const val KEY_NUMBER = "exercise_list_fragment_key"
        private const val KEY_PARAMS = "exercise_key"

        fun createBundle(data: Parcelable?): Bundle {
            return Bundle().apply {
                when (data) {
                    is LibraryParams.Exercise -> putParcelable(KEY_PARAMS, data)
                    else -> {
                        if (BuildConfig.DEBUG) {
                            throw (
                                IllegalArgumentException(
                                    "data should be NewExerciseParams.Exercise type."
                                )
                                )
                        }
                    }
                }
            }
        }
    }
}
