package com.lefarmico.donetime.views.fragments.listMenu

import android.os.Bundle
import androidx.core.os.bundleOf
import androidx.fragment.app.Fragment
import com.lefarmico.donetime.R
import com.lefarmico.donetime.adapters.ExerciseLibraryAdapter
import com.lefarmico.donetime.data.entities.currentExercise.ExerciseName
import com.lefarmico.donetime.data.entities.library.ILibraryItem
import com.lefarmico.donetime.databinding.FragmentExerciseListBinding
import com.lefarmico.donetime.viewModels.ExerciseListViewModel
import com.lefarmico.donetime.views.base.BaseFragment
import com.lefarmico.donetime.views.fragments.AddExerciseFragment
import com.lefarmico.donetime.views.fragments.WorkoutScreenFragment

abstract class ExerciseListFragment : BaseFragment<FragmentExerciseListBinding, ExerciseListViewModel>(
    FragmentExerciseListBinding::inflate,
    ExerciseListViewModel::class.java
) {

    abstract val onItemClickListener: (ILibraryItem) -> Unit
    
    var bundleResult: Int = -1
    private val bundle = Bundle()

    private val adapter = ExerciseLibraryAdapter()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        getBundleResult()
    }

    override fun setUpViews() {
        adapter.onClick = onItemClickListener
        binding.recycler.adapter = adapter
        viewModel.passExercisesToLiveData(bundleResult)

        binding.editButton.setOnClickListener {
            bundle.putInt(AddExerciseFragment.KEY_NUMBER, bundleResult)
            changeFragment(AddExerciseFragment::class.java, bundle)
        }
    }

    override fun observeData() {
        viewModel.exercisesLiveData.observe(viewLifecycleOwner) { list ->
            adapter.items = list
        }
    }

    protected fun setExerciseResult(exerciseEntity: ExerciseName) {
        parentFragmentManager.setFragmentResult(
            WorkoutScreenFragment.REQUEST_KEY,
            bundleOf(WorkoutScreenFragment.KEY_NUMBER to exerciseEntity)
        )
    }

    private fun getBundleResult() {
        val bundle = this.arguments
        if (bundle != null) {
            bundleResult = bundle.getInt(SubCategoryListFragment.KEY_NUMBER)
        }
    }

    private fun changeFragment(
        fragment: Class<out Fragment>,
        bundle: Bundle
    ) {
        parentFragmentManager.beginTransaction()
            .replace(R.id.fragment, fragment, bundle)
            .addToBackStack(AddExerciseFragment.BACK_STACK_KEY)
            .commit()
    }
}
