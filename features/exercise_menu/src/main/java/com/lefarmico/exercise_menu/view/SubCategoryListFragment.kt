package com.lefarmico.exercise_menu.view

import android.os.Bundle
import android.os.Parcelable
import com.lefarmico.core.BuildConfig
import com.lefarmico.core.adapter.ExerciseLibraryAdapter
import com.lefarmico.core.base.BaseFragment
import com.lefarmico.domain.entity.LibraryDto
import com.lefarmico.domain.utils.DataState
import com.lefarmico.exercise_menu.databinding.FragmentSubcategoryListBinding
import com.lefarmico.exercise_menu.intent.SubCategoryIntent
import com.lefarmico.exercise_menu.viewModel.SubCategoryViewModel
import com.lefarmico.navigation.params.LibraryParams
import java.lang.IllegalArgumentException

class SubCategoryListFragment : BaseFragment<FragmentSubcategoryListBinding, SubCategoryViewModel>(
    FragmentSubcategoryListBinding::inflate,
    SubCategoryViewModel::class.java
) {

    private val params: LibraryParams.SubcategoryList by lazy {
        arguments?.getParcelable<LibraryParams.SubcategoryList>(KEY_PARAMS)
            ?: throw (IllegalArgumentException("Arguments params must be not null"))
    }
    private val adapter = ExerciseLibraryAdapter()

    override fun setUpViews() {
        viewModel.onTriggerEvent(SubCategoryIntent.GetSubcategories(params.categoryId))

        binding.apply {
            recycler.adapter = adapter
            plusButton.setOnClickListener {
                binding.textField.apply {
                    viewModel.onTriggerEvent(
                        SubCategoryIntent.AddNewSubCategory(editText?.text.toString(), params.categoryId)
                    )
                }
            }
        }
        adapter.apply {
            onClick = { item ->
                item as LibraryDto.SubCategory
                viewModel.onTriggerEvent(
                    SubCategoryIntent.GoToExerciseListScreen(
                        params.categoryId,
                        item.id,
                        params.isFromWorkoutScreen
                    )
                )
            }
        }
    }

    override fun observeData() {
        viewModel.subCategoriesLiveData.observe(viewLifecycleOwner) { dataState ->
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

    companion object {
        private const val KEY_PARAMS = "subcategory_key"

        fun createBundle(data: Parcelable?): Bundle {
            return Bundle().apply {
                when (data) {
                    is LibraryParams.SubcategoryList -> putParcelable(KEY_PARAMS, data)
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
