package com.lefarmico.exercise_menu.view

import android.os.Bundle
import com.lefarmico.core.adapter.ExerciseLibraryAdapter
import com.lefarmico.core.base.BaseFragment
import com.lefarmico.domain.entity.LibraryDto
import com.lefarmico.domain.utils.DataState
import com.lefarmico.exercise_menu.databinding.FragmentCategoriesBinding
import com.lefarmico.exercise_menu.intent.CategoryListIntent
import com.lefarmico.exercise_menu.viewModel.CategoryListViewModel

abstract class CategoryListFragment : BaseFragment<FragmentCategoriesBinding, CategoryListViewModel>(
    FragmentCategoriesBinding::inflate,
    CategoryListViewModel::class.java
) {

    abstract val branchSubcategoryFragment: Class<out SubCategoryListFragment>

    private val adapter = ExerciseLibraryAdapter().apply {
        val bundle = Bundle()
        onClick = { item ->
            item as LibraryDto.Category
            bundle.putInt(KEY_NUMBER, item.id)
            changeFragment(branchSubcategoryFragment, bundle)
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

    private fun changeFragment(
        fragment: Class<out SubCategoryListFragment>,
        bundle: Bundle
    ) {
        // TODO navigation module
//        parentFragmentManager.beginTransaction()
//            .replace(R.id.fragment, fragment, bundle)
//            .addToBackStack(com.lefarmico.workout.view.WorkoutScreenFragment.BACKSTACK_BRANCH)
//            .commit()
    }

    companion object {
        const val KEY_NUMBER = "key_category"
    }
}
