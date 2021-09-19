package com.lefarmico.exercise_menu.view

import android.os.Bundle
import com.lefarmico.core.adapter.ExerciseLibraryAdapter
import com.lefarmico.core.base.BaseFragment
import com.lefarmico.domain.entity.LibraryDto
import com.lefarmico.domain.utils.DataState
import com.lefarmico.exercise_menu.databinding.FragmentSubcategoryListBinding
import com.lefarmico.exercise_menu.intent.SubCategoryIntent
import com.lefarmico.exercise_menu.viewModel.SubCategoryViewModel

abstract class SubCategoryListFragment : BaseFragment<FragmentSubcategoryListBinding, SubCategoryViewModel>(
    FragmentSubcategoryListBinding::inflate,
    SubCategoryViewModel::class.java
) {

    abstract val branchExerciseFragment: Class<out ExerciseListFragment>

    private var bundleData: Int? = null
    private val adapter = ExerciseLibraryAdapter()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
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
        fragment: Class<out ExerciseListFragment>,
        bundle: Bundle
    ) {
        // TODO navigation module
//        parentFragmentManager.beginTransaction()
//            .replace(R.id.fragment, fragment, bundle)
//            .addToBackStack(com.lefarmico.workout.view.WorkoutScreenFragment.BACKSTACK_BRANCH)
//            .commit()
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
