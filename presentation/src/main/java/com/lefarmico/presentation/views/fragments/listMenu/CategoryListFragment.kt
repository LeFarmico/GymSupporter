package com.lefarmico.presentation.views.fragments.listMenu

import android.os.Bundle
import android.view.View
import com.lefarmico.domain.entity.LibraryDto
import com.lefarmico.domain.utils.DataState
import com.lefarmico.presentation.R
import com.lefarmico.presentation.adapters.ExerciseLibraryAdapter
import com.lefarmico.presentation.databinding.FragmentCategoryListBinding
import com.lefarmico.presentation.di.provider.PresentationComponentProvider
import com.lefarmico.presentation.intents.CategoryListIntent
import com.lefarmico.presentation.viewModels.CategoryListViewModel
import com.lefarmico.presentation.views.base.BaseFragment
import com.lefarmico.presentation.views.fragments.WorkoutScreenFragment

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

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        (activity?.application as PresentationComponentProvider)
            .getPresentationComponent()
            .inject(viewModel)
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
