package com.lefarmico.core.dialog

import android.app.AlertDialog
import android.app.Dialog
import android.os.Bundle
import androidx.fragment.app.DialogFragment
import androidx.recyclerview.widget.DividerItemDecoration
import com.lefarmico.core.adapter.MenuAdapter
import com.lefarmico.core.databinding.DialogListBinding

class ListItemPickerDialog(
    private val itemList: List<String>,
    private val callbackPosition: (Int) -> Unit
) : DialogFragment() {

    private var _binding: DialogListBinding? = null
    private val binding get() = _binding!!
    private val inflater get() = requireActivity().layoutInflater

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        val builder = AlertDialog.Builder(requireContext())
        inflate(builder)

        binding.root.addItemDecoration(
            DividerItemDecoration(requireContext(), DividerItemDecoration.VERTICAL), 0
        )
        binding.root.adapter = MenuAdapter().apply {
            this.items = itemList
            onClick = {
                callbackPosition(it)
                dialog?.cancel()
            }
        }

        return builder.create()
    }

    fun inflate(builder: AlertDialog.Builder) {
        _binding = DialogListBinding.inflate(inflater)
        builder.setView(binding.root)
    }

    companion object {
        const val TAG = "ListItemPickerDialog"
    }
}
