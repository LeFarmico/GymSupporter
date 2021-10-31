package com.lefarmico.exercise_menu.view

import android.os.Bundle
import android.os.Parcelable
import com.lefarmico.core.BuildConfig
import com.lefarmico.core.adapter.ExerciseLibraryAdapter
import com.lefarmico.core.base.BaseFragment
import com.lefarmico.core.entity.LibraryViewData
import com.lefarmico.core.utils.EmptyTextValidator
import com.lefarmico.domain.utils.DataState
import com.lefarmico.exercise_menu.databinding.FragmentSubcategoryListBinding
import com.lefarmico.exercise_menu.intent.SubCategoryIntent
import com.lefarmico.exercise_menu.intent.SubCategoryIntent.*
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
        startEvent(GetSubcategories(params.categoryId))

        binding.apply {
            recycler.adapter = adapter
            editText.addTextChangedListener(
                EmptyTextValidator(editText, textField)
            )
            plusButton.setOnClickListener {
                binding.textField.apply {
                    startEvent(
                        AddNewSubCategory(editText?.text.toString(), params.categoryId)
                    )
                }
            }
        }
        adapter.apply {
            onClick = { item ->
                item as LibraryViewData.SubCategory
                startEvent(
                    GoToExerciseListScreen(
                        categoryId = params.categoryId,
                        subcategoryId = item.id,
                        isFromWorkoutScreen = params.isFromWorkoutScreen
                    )
                )
            }
        }
    }

    override fun observeData() {
        viewModel.notificationLiveData.observe(viewLifecycleOwner) { notification ->
            startEvent(ShowToast(notification))
        }
        viewModel.subCategoriesLiveData.observe(viewLifecycleOwner) { dataState ->
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

    private fun startEvent(event: SubCategoryIntent) {
        viewModel.onTriggerEvent(event)
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
