package com.lefarmico.donetime.views.fragments.listMenu.library

import android.os.Bundle
import android.widget.Toast
import com.lefarmico.domain.entity.LibraryDto
import com.lefarmico.donetime.R
import com.lefarmico.donetime.views.fragments.listMenu.ExerciseDetailsFragment
import com.lefarmico.donetime.views.fragments.listMenu.ExerciseListFragment

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