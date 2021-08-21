package com.lefarmico.donetime.views.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import com.lefarmico.donetime.R
import com.lefarmico.donetime.adapters.ExerciseLibraryAdapter
import com.lefarmico.donetime.data.entities.library.ItemLibraryCategory
import com.lefarmico.donetime.databinding.FragmentCategoryListBinding
import com.lefarmico.donetime.viewModels.CategoryListViewModel
import com.lefarmico.donetime.views.base.BaseFragment

class CategoryListFragment : BaseFragment<FragmentCategoryListBinding, CategoryListViewModel>(
    FragmentCategoryListBinding::inflate,
    CategoryListViewModel::class.java
) {

    private val adapter = ExerciseLibraryAdapter().apply {
        val bundle = Bundle()
        onClick = { item ->
            item as ItemLibraryCategory
            bundle.putInt(KEY_NUMBER, item.id)
            changeFragment(SubCategoryListFragment::class.java, bundle)
        }
    }

    override fun setUpViews() {
        binding.recycler.adapter = adapter
    }

    override fun observeData() {
        viewModel.categoriesLiveData.observe(viewLifecycleOwner) { data ->
            adapter.items = data
        }
    }

    private fun changeFragment(
        fragment: Class<out Fragment>,
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
