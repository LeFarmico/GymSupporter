package com.lefarmico.donetime.views.fragments

import com.lefarmico.donetime.adapters.ExerciseListAdapter
import com.lefarmico.donetime.databinding.FragmentExerciseListBinding
import com.lefarmico.donetime.viewModels.ExerciseListViewModel
import com.lefarmico.donetime.views.base.BaseFragment

class ExerciseListFragment : BaseFragment<FragmentExerciseListBinding, ExerciseListViewModel>(
    FragmentExerciseListBinding::inflate,
    ExerciseListViewModel::class.java
) {

    override fun setUpViews() {
        val adapter = ExerciseListAdapter()
        viewModel.categoriesLiveData.observe(viewLifecycleOwner) {
            adapter.setCategories(it)
            binding.recycler.adapter = adapter
        }
    }

    override fun observeView() {
        super.observeView()
    }

    override fun observeData() {
        super.observeData()
    }
}
