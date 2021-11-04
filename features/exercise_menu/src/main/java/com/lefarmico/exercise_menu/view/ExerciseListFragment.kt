package com.lefarmico.exercise_menu.view

import android.os.Bundle
import android.os.Parcelable
import androidx.recyclerview.widget.DividerItemDecoration
import com.lefarmico.core.BuildConfig
import com.lefarmico.core.adapter.ExerciseLibraryAdapter
import com.lefarmico.core.base.BaseFragment
import com.lefarmico.core.entity.LibraryViewData
import com.lefarmico.domain.utils.DataState
import com.lefarmico.exercise_menu.databinding.FragmentExerciseListBinding
import com.lefarmico.exercise_menu.intent.ExerciseListIntent
import com.lefarmico.exercise_menu.viewModel.ExerciseListViewModel
import com.lefarmico.navigation.params.LibraryParams
import java.lang.IllegalArgumentException

class ExerciseListFragment : BaseFragment<FragmentExerciseListBinding, ExerciseListViewModel>(
    FragmentExerciseListBinding::inflate,
    ExerciseListViewModel::class.java
) {

    private val params: LibraryParams.ExerciseList by lazy {
        arguments?.getParcelable<LibraryParams.ExerciseList>(KEY_PARAMS)
            ?: throw (IllegalArgumentException("Arguments params must be not null"))
    }

    private val onItemClickListener: (LibraryViewData) -> Unit =
        { item ->
            item as LibraryViewData.Exercise
            if (params.isFromWorkoutScreen) {
                viewModel.onTriggerEvent(
                    ExerciseListIntent.AddExerciseToWorkoutScreen(item.id)
                )
            } else {
                viewModel.onTriggerEvent(
                    ExerciseListIntent.GoToExerciseDetailsScreen(item.id)
                )
            }
        }

    private val adapter = ExerciseLibraryAdapter()

    override fun setUpViews() {
        adapter.onClick = onItemClickListener
        binding.recycler.adapter = adapter
        viewModel.onTriggerEvent(
            ExerciseListIntent.GetExercises(params.subCategoryId)
        )

        if (binding.recycler.itemDecorationCount == 0) {
            val decorator = DividerItemDecoration(context, DividerItemDecoration.VERTICAL)
            binding.recycler.addItemDecoration(decorator)
        }
        binding.plusButton.setOnClickListener {
            viewModel.onTriggerEvent(
                ExerciseListIntent.CreateNewExercise(
                    params.categoryId,
                    params.subCategoryId,
                    params.isFromWorkoutScreen
                )
            )
        }
    }

    override fun observeData() {
        viewModel.exercisesLiveData.observe(viewLifecycleOwner) { dataState ->
            when (dataState) {
                DataState.Empty -> {
                    adapter.items = mutableListOf()
                    binding.state.showEmptyState()
                }
                is DataState.Error -> {
                    adapter.items = mutableListOf()
                    binding.state.showErrorState()
                }
                DataState.Loading -> {
                    adapter.items = mutableListOf()
                    binding.state.showLoadingState()
                }
                is DataState.Success -> {
                    adapter.items = dataState.data
                    binding.state.showSuccessState()
                }
            }
        }
    }

    companion object {
        private const val KEY_PARAMS = "exercise_key"

        fun createBundle(data: Parcelable?): Bundle {
            return Bundle().apply {
                when (data) {
                    is LibraryParams.ExerciseList -> putParcelable(KEY_PARAMS, data)
                    else -> {
                        if (BuildConfig.DEBUG) {
                            throw (
                                IllegalArgumentException(
                                    "data should be LibraryParams.ExerciseList type." +
                                        "but it's ${data!!.javaClass.simpleName}"
                                )
                                )
                        }
                    }
                }
            }
        }
    }
}
