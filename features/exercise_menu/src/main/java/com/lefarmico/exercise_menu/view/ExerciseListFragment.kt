package com.lefarmico.exercise_menu.view

import android.os.Bundle
import android.os.Parcelable
import androidx.recyclerview.widget.DividerItemDecoration
import com.lefarmico.core.BuildConfig
import com.lefarmico.core.adapter.LibraryItemAdapter
import com.lefarmico.core.base.BaseFragment
import com.lefarmico.core.entity.LibraryViewData
import com.lefarmico.exercise_menu.action.ExerciseAction
import com.lefarmico.exercise_menu.databinding.FragmentExerciseListBinding
import com.lefarmico.exercise_menu.intent.ExerciseIntent
import com.lefarmico.exercise_menu.intent.ExerciseIntent.*
import com.lefarmico.exercise_menu.state.LibraryListEvent
import com.lefarmico.exercise_menu.state.LibraryListState
import com.lefarmico.exercise_menu.viewModel.ExerciseListViewModel
import com.lefarmico.navigation.params.LibraryParams
import java.lang.IllegalArgumentException

class ExerciseListFragment :
    BaseFragment<
        ExerciseIntent, ExerciseAction, LibraryListState, LibraryListEvent,
        FragmentExerciseListBinding, ExerciseListViewModel>(
        FragmentExerciseListBinding::inflate,
        ExerciseListViewModel::class.java
    ) {

    private val params: LibraryParams.ExerciseList by lazy {
        arguments?.getParcelable<LibraryParams.ExerciseList>(KEY_PARAMS)
            ?: throw (IllegalArgumentException("Arguments params must be not null"))
    }

    private val onItemClickListener: (LibraryViewData) -> Unit = { item ->
        item as LibraryViewData.Exercise
        dispatchIntent(ClickItem(item, params.isFromWorkoutScreen))
    }

    private val adapter = LibraryItemAdapter()

    override fun setUpViews() {
        adapter.onClick = onItemClickListener
        binding.recycler.adapter = adapter
        val subCategoryId = params.subCategoryId
        dispatchIntent(GetExercises(subCategoryId))

        val decorator = DividerItemDecoration(context, DividerItemDecoration.VERTICAL)
        binding.recycler.addItemDecoration(decorator, 0)

        binding.plusButton.setOnClickListener {
            dispatchIntent(CreateNewExercise(params.subCategoryId, params.isFromWorkoutScreen))
        }
    }

    private fun showExercises(items: List<LibraryViewData>) {
        adapter.items = items
        if (items.isEmpty()) {
            binding.state.showEmptyState()
            return
        }
        binding.state.showSuccessState()
    }

    private fun showLoading() {
        binding.state.showLoadingState()
    }

    override fun receive(state: LibraryListState) {
        when (state) {
            is LibraryListState.ExceptionResult -> throw (state.exception)
            is LibraryListState.LibraryResult -> showExercises(state.libraryList)
            LibraryListState.Loading -> showLoading()
        }
    }

    override fun receive(event: LibraryListEvent) {
        when (event) {
            is LibraryListEvent.ShowToast -> {}
            LibraryListEvent.ValidationResult.AlreadyExist -> {}
            LibraryListEvent.ValidationResult.Empty -> {}
            LibraryListEvent.ValidationResult.Success -> {}
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
