package com.lefarmico.donetime.views.fragments.listMenu

import android.os.Bundle
import com.lefarmico.donetime.R
import com.lefarmico.donetime.adapters.ExerciseLibraryAdapter
import com.lefarmico.donetime.data.entities.library.LibraryCategory
import com.lefarmico.donetime.databinding.FragmentCategoryListBinding
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
            item as LibraryCategory
            bundle.putInt(KEY_NUMBER, item.id)
            changeFragment(branchSubcategoryFragment, bundle)
        }
    }

    override fun setUpViews() {
        binding.recycler.adapter = adapter

        binding.editButton.setOnClickListener {
            binding.textField.apply {
                viewModel.addNewCategory(editText?.text.toString())
            }
        }
    }

    override fun observeData() {
        viewModel.categoriesLiveData.observe(viewLifecycleOwner) { data ->
            adapter.items = data
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
