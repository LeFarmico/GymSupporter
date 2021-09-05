package com.lefarmico.donetime.views.fragments.listMenu

import android.os.Bundle
import com.lefarmico.domain.entity.LibraryDto
import com.lefarmico.domain.utils.DataState
import com.lefarmico.donetime.R
import com.lefarmico.donetime.adapters.ExerciseLibraryAdapter
import com.lefarmico.donetime.databinding.FragmentCategoryListBinding
import com.lefarmico.donetime.intents.CategoryListIntent
import com.lefarmico.donetime.viewModels.CategoryListViewModel
import com.lefarmico.donetime.views.base.BaseFragment
import com.lefarmico.donetime.views.fragments.WorkoutScreenFragment

abstract class CategoryListFragment : BaseFragment<FragmentCategoryListBinding, CategoryListViewModel>(
    FragmentCategoryListBinding::inflate,
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
        binding.recycler.adapter = adapter

        viewModel.onTriggerEvent(CategoryListIntent.GetCategories)

        binding.plusButton.setOnClickListener {
            binding.textField.apply {
                viewModel.onTriggerEvent(
                    CategoryListIntent.AddCategory(
                        editText?.text.toString()
                    )
                )
            }
        }
    }

    override fun observeData() {
        viewModel.categoriesLiveData.observe(viewLifecycleOwner) { dataState ->
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

    private fun changeFragment(
        fragment: Class<out SubCategoryListFragment>,
        bundle: Bundle
    ) {
        parentFragmentManager.beginTransaction()
            .replace(R.id.fragment, fragment, bundle)
            .addToBackStack(WorkoutScreenFragment.BACKSTACK_BRANCH)
            .commit()
    }
    
    companion object {
        const val KEY_NUMBER = "key_category"
    }
}
