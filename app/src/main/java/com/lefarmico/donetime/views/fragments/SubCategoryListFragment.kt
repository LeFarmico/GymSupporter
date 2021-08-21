package com.lefarmico.donetime.views.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import com.lefarmico.donetime.R
import com.lefarmico.donetime.adapters.ExerciseLibraryAdapter
import com.lefarmico.donetime.data.entities.library.ItemLibrarySubCategory
import com.lefarmico.donetime.databinding.FragmentSubcategoryListBinding
import com.lefarmico.donetime.viewModels.SubCategoryViewModel
import com.lefarmico.donetime.views.base.BaseFragment

class SubCategoryListFragment : BaseFragment<FragmentSubcategoryListBinding, SubCategoryViewModel>(
    FragmentSubcategoryListBinding::inflate,
    SubCategoryViewModel::class.java
) {

    private val adapter = ExerciseLibraryAdapter().apply {
        val bundle = Bundle()
        onClick = { item ->
            item as ItemLibrarySubCategory
            bundle.putInt(KEY_NUMBER, item.id)
            changeFragment(ExerciseListFragment::class.java, bundle)
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val bundle = this.arguments
        if (bundle != null) {
            viewModel.passSubCategoryToLiveData(
                bundle.getInt(CategoryListFragment.KEY_NUMBER)
            )
        }
    }
    override fun setUpViews() {
        binding.recycler.adapter = adapter
    }

    override fun observeData() {
        viewModel.subCategoriesLiveData.observe(viewLifecycleOwner) {
            adapter.items = it
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
        const val KEY_NUMBER = "key_sub_category"
    }
}
