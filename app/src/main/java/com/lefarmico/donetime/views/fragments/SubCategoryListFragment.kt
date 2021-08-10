package com.lefarmico.donetime.views.fragments

import android.os.Bundle
import com.lefarmico.donetime.R
import com.lefarmico.donetime.adapters.ExerciseListAdapter
import com.lefarmico.donetime.data.entities.library.ItemLibrarySubCategory
import com.lefarmico.donetime.databinding.FragmentSubcategoryListBinding
import com.lefarmico.donetime.viewModels.SubCategoryViewModel
import com.lefarmico.donetime.views.base.BaseFragment

class SubCategoryListFragment : BaseFragment<FragmentSubcategoryListBinding, SubCategoryViewModel>(
    FragmentSubcategoryListBinding::inflate,
    SubCategoryViewModel::class.java
) {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val bundle = this.arguments
        if (bundle != null) {
            viewModel.passSubCategoryToLiveData(
                bundle.getInt("Category")
            )
        }
    }
    override fun setUpViews() {
        val adapter = ExerciseListAdapter()
        viewModel.subCategoriesLiveData.observe(viewLifecycleOwner) {
            val bundle = Bundle()
            adapter.setSubCategories(it)
            adapter.setOnClickEvent { item ->
                item as ItemLibrarySubCategory
                bundle.putInt("SubCategory", item.id)
                parentFragmentManager.beginTransaction()
                    .replace(R.id.fragment, ExerciseListFragment::class.java, bundle)
                    .addToBackStack("EX")
                    .commit()
            }
        }
        binding.recycler.adapter = adapter
    }
}
