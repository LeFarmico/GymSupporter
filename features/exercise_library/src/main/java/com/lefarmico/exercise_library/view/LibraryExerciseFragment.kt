package com.lefarmico.exercise_library.view

import android.os.Bundle
import android.widget.Toast
import com.lefarmico.domain.entity.LibraryDto

class LibraryExerciseFragment : com.lefarmico.exercise_menu.view.ExerciseListFragment() {

    override val onItemClickListener: (LibraryDto) -> Unit = {
        it as LibraryDto.Exercise
        Toast.makeText(requireContext(), it.title, Toast.LENGTH_SHORT).show()
        setResult(it.id)
    }

    private fun setResult(exerciseId: Int) {
        val bundle = Bundle()
        bundle.putInt(KEY_NUMBER, exerciseId)
        // TODO navigation module
//        parentFragmentManager.beginTransaction()
//            .replace(R.id.fragment, ExerciseDetailsFragment::class.java, bundle)
//            .commit()
    }
}
