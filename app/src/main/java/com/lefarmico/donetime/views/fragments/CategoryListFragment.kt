package com.lefarmico.donetime.views.fragments

import android.os.Bundle
import com.lefarmico.donetime.R
import com.lefarmico.donetime.adapters.ExerciseListAdapter
import com.lefarmico.donetime.data.entities.library.ItemLibraryCategory
import com.lefarmico.donetime.databinding.FragmentCategoryListBinding
import com.lefarmico.donetime.viewModels.CategoryListViewModel
import com.lefarmico.donetime.views.base.BaseFragment

class CategoryListFragment : BaseFragment< FragmentCategoryListBinding, CategoryListViewModel>(
    FragmentCategoryListBinding::inflate,
    CategoryListViewModel::class.java
) {

    override fun setUpViews() {
        val adapter = ExerciseListAdapter()
        viewModel.categoriesLiveData.observe(viewLifecycleOwner) { data ->
            adapter.setCategories(data)
            val bundle = Bundle()
            adapter.setOnClickEvent { item ->
                item as ItemLibraryCategory
                bundle.putInt("Category", item.id)
                parentFragmentManager.beginTransaction()
                    .replace(R.id.fragment, SubCategoryListFragment::class.java, bundle)
                    .addToBackStack("EX")
                    .commit()
            }
        }
        binding.recycler.adapter = adapter
    }
}
