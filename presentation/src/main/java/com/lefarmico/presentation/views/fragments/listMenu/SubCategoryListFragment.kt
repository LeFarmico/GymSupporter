package com.lefarmico.donetime.views.fragments.listMenu

import android.os.Bundle
import com.lefarmico.donetime.R
import com.lefarmico.donetime.adapters.ExerciseLibraryAdapter
import com.lefarmico.donetime.data.entities.library.LibrarySubCategory
import com.lefarmico.donetime.databinding.FragmentSubcategoryListBinding
import com.lefarmico.donetime.viewModels.SubCategoryViewModel
import com.lefarmico.donetime.views.base.BaseFragment
import com.lefarmico.donetime.views.fragments.WorkoutScreenFragment

abstract class SubCategoryListFragment : BaseFragment<FragmentSubcategoryListBinding, SubCategoryViewModel>(
    FragmentSubcategoryListBinding::inflate,
    SubCategoryViewModel::class.java
) {

    abstract val branchExerciseFragment: Class<out ExerciseListFragment>

    private var bundleData: Int? = null
    private val adapter = ExerciseLibraryAdapter().apply {
        val bundle = Bundle()
        onClick = { item ->
            item as LibrarySubCategory
            bundle.putInt(KEY_NUMBER, item.id)
            changeFragment(branchExerciseFragment, bundle)
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val bundle = this.arguments
        if (bundle != null) {
            bundleData = bundle.getInt(CategoryListFragment.KEY_NUMBER)
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
        binding.editButton.setOnClickListener {
            binding.textField.apply {
                viewModel.addNewSubCategory(editText?.text.toString(), bundleData)
            }
        }
    }

    private fun changeFragment(
        fragment: Class<out ExerciseListFragment>,
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
