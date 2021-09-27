package com.lefarmico.exercise_menu.view

import android.os.Bundle
import android.os.Parcelable
import com.lefarmico.core.BuildConfig
import com.lefarmico.core.adapter.ExerciseLibraryAdapter
import com.lefarmico.core.base.BaseFragment
import com.lefarmico.domain.entity.LibraryDto
import com.lefarmico.domain.utils.DataState
import com.lefarmico.exercise_menu.databinding.FragmentCategoriesBinding
import com.lefarmico.exercise_menu.intent.CategoryListIntent
import com.lefarmico.exercise_menu.viewModel.CategoryListViewModel
import com.lefarmico.navigation.params.LibraryParams
import java.lang.IllegalArgumentException

class CategoryListFragment : BaseFragment<FragmentCategoriesBinding, CategoryListViewModel>(
    FragmentCategoriesBinding::inflate,
    CategoryListViewModel::class.java
) {

    private val params: LibraryParams.CategoryList by lazy {
        arguments?.getParcelable(KEY_PARAMS)
            ?: LibraryParams.CategoryList(isFromWorkoutScreen = false)
    }

    private val adapter = ExerciseLibraryAdapter().apply {
        onClick = { item ->
            item as LibraryDto.Category
            viewModel.onTriggerEvent(
                CategoryListIntent.GoToSubcategoryScreen(
                    item.id,
                    params.isFromWorkoutScreen
                )
            )
        }
    }

    override fun setUpViews() {
        viewModel.onTriggerEvent(CategoryListIntent.GetCategories)

        binding.apply {
            recycler.adapter = adapter
            plusButton.setOnClickListener {
                textField.apply {
                    viewModel.onTriggerEvent(
                        CategoryListIntent.AddCategory(
                            editText?.text.toString()
                        )
                    )
                }
            }
        }
    }

    override fun observeData() {
        viewModel.categoriesLiveData.observe(viewLifecycleOwner) { dataState ->
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
        private const val KEY_PARAMS = "category_key"

        fun createBundle(data: Parcelable?): Bundle {
            return Bundle().apply {
                when (data) {
                    is LibraryParams.CategoryList -> putParcelable(KEY_PARAMS, data)
                    else -> {
                        if (BuildConfig.DEBUG) {
                            throw (
                                IllegalArgumentException(
                                    "data should be LibraryParams.CategoryList type." +
                                        "but it's type ${data!!.javaClass.canonicalName}"
                                )
                                )
                        }
                    }
                }
            }
        }
    }
}
