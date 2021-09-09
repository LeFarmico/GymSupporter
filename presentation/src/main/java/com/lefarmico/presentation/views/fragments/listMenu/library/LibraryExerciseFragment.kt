package com.lefarmico.presentation.views.fragments.listMenu.library

import android.os.Bundle
import android.widget.Toast
import com.lefarmico.domain.entity.LibraryDto
import com.lefarmico.presentation.R
import com.lefarmico.presentation.views.fragments.listMenu.ExerciseDetailsFragment
import com.lefarmico.presentation.views.fragments.listMenu.ExerciseListFragment

class LibraryExerciseFragment : ExerciseListFragment() {
    
    override val onItemClickListener: (LibraryDto) -> Unit = {
        it as LibraryDto.Exercise
        Toast.makeText(requireContext(), it.title, Toast.LENGTH_SHORT).show()
        setResult(it.id)
    }

    private fun setResult(exerciseId: Int) {
        val bundle = Bundle()
        bundle.putInt(KEY_NUMBER, exerciseId)
        parentFragmentManager.beginTransaction()
            .replace(R.id.fragment, ExerciseDetailsFragment::class.java, bundle)
            .commit()
    }
}
