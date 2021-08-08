package com.lefarmico.donetime.views.fragments

import com.lefarmico.donetime.adapters.ExerciseListAdapter
import com.lefarmico.donetime.adapters.ExerciseListViewHolderFactory
import com.lefarmico.donetime.data.entities.library.ItemLibraryCategory
import com.lefarmico.donetime.data.entities.library.ItemLibrarySubCategory
import com.lefarmico.donetime.databinding.FragmentExerciseListBinding
import com.lefarmico.donetime.viewModels.ExerciseListViewModel
import com.lefarmico.donetime.views.base.BaseFragment

class ExerciseListFragment : BaseFragment<FragmentExerciseListBinding, ExerciseListViewModel>(
    FragmentExerciseListBinding::inflate,
    ExerciseListViewModel::class.java
) {

    override fun setUpViews() {
        val adapter = ExerciseListAdapter()
        adapter.setOnClickEvent {
            when (it.type) {
                ExerciseListViewHolderFactory.CATEGORY -> {
                    it as ItemLibraryCategory
                    viewModel.passSubCategoryToLiveData(it.id)
                }
                ExerciseListViewHolderFactory.SUBCATEGORY -> {
                    it as ItemLibrarySubCategory
                    viewModel.passExercisesToLiveData(it.id)
                }
            }
        }
        binding.recycler.adapter = adapter
        viewModel.categoriesLiveData.observe(viewLifecycleOwner) {
            adapter.setCategories(it)
        }

        viewModel.subCategoriesLiveData.observe(viewLifecycleOwner) {
            adapter.setSubCategories(it)
        }

        viewModel.exercisesLiveData.observe(viewLifecycleOwner) {
            adapter.setExercises(it)
        }
    }

    override fun observeView() {
        super.observeView()
    }

    override fun observeData() {
        super.observeData()
    }
}
