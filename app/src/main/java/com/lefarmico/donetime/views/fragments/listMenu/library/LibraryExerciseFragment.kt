package com.lefarmico.donetime.views.fragments.listMenu.library

import android.widget.Toast
import com.lefarmico.donetime.data.entities.library.ILibraryItem
import com.lefarmico.donetime.views.fragments.listMenu.ExerciseListFragment

class LibraryExerciseFragment : ExerciseListFragment() {
    
    override val onItemClickListener: (ILibraryItem) -> Unit = {
        Toast.makeText(requireContext(), it.title, Toast.LENGTH_SHORT).show()
    }
}
