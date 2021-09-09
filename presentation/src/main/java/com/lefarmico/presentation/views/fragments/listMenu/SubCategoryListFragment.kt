package com.lefarmico.presentation.views.fragments.listMenu

import android.os.Bundle
import android.view.View
import com.lefarmico.domain.entity.LibraryDto
import com.lefarmico.domain.utils.DataState
import com.lefarmico.presentation.R
import com.lefarmico.presentation.adapters.ExerciseLibraryAdapter
import com.lefarmico.presentation.databinding.FragmentSubcategoryListBinding
import com.lefarmico.presentation.di.provider.PresentationComponentProvider
import com.lefarmico.presentation.intents.SubCategoryIntent
import com.lefarmico.presentation.viewModels.SubCategoryViewModel
import com.lefarmico.presentation.views.base.BaseFragment
import com.lefarmico.presentation.views.fragments.WorkoutScreenFragment

abstract class SubCategoryListFragment : BaseFragment<FragmentSubcategoryListBinding, SubCategoryViewModel>(
    FragmentSubcategoryListBinding::inflate,
    SubCategoryViewModel::class.java
) {

    abstract val branchExerciseFragment: Class<out ExerciseListFragment>

    private var bundleData: Int? = null
    private val adapter = ExerciseLibraryAdapter()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        (activity?.application as PresentationComponentProvider)
            .getPresentationComponent()
            .inject(viewModel)

        getBundleResult()
    }

    override fun setUpViews() {
        viewModel.onTriggerEvent(SubCategoryIntent.GetSubcategories(bundleData!!))

        binding.apply {
            recycler.adapter = adapter
            plusButton.setOnClickListener {
                binding.textField.apply {
                    viewModel.onTriggerEvent(
                        SubCategoryIntent.AddNewSubCategory(editText?.text.toString(), bundleData!!)
                    )
                }
            }
        }
        adapter.apply {
            val bundle = Bundle()
            onClick = { item ->
                item as LibraryDto.SubCategory
                bundle.putInt(KEY_NUMBER, item.id)
                changeFragment(branchExerciseFragment, bundle)
            }
        }
    }

    override fun observeData() {
        viewModel.subCategoriesLiveData.observe(viewLifecycleOwner) { dataState ->
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
                    binding.emptyState.root.visibility = View.GONE
                    binding.errorState.root.visibility = View.GONE
                    binding.loadingState.root.visibility = View.GONE

                    adapter.items = dataState.data
                }
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

    private fun getBundleResult() {
        val bundle = this.arguments
        if (bundle != null) {
            bundleData = bundle.getInt(CategoryListFragment.KEY_NUMBER)
        }
    }

    companion object {
        const val KEY_NUMBER = "key_sub_category"
    }
}
