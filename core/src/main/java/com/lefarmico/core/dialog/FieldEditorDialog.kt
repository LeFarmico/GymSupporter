package com.lefarmico.core.dialog

import android.app.AlertDialog
import android.app.Dialog
import android.os.Bundle
import android.view.inputmethod.EditorInfo
import android.widget.EditText
import androidx.fragment.app.DialogFragment
import com.lefarmico.core.R
import com.lefarmico.core.databinding.DialogFieldEditorBinding
import com.lefarmico.core.extensions.hideSoftKeyboard

class FieldEditorDialog(
    private val hint: String = "",
    private val callback: (String) -> Unit = {}
) : DialogFragment() {

    private var _binding: DialogFieldEditorBinding? = null
    private val binding get() = _binding!!
    private val inflater get() = requireActivity().layoutInflater

    private val textField get() = binding.editText.text.toString()

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        val builder = AlertDialog.Builder(requireContext(), R.style.AlertDialogTheme)
        inflate(builder)

        binding.textField.hint = hint

        binding.editText.setOnEditorActionListener { _, actionId, _ ->
            when (actionId) {
                EditorInfo.IME_ACTION_DONE -> {
                    if (textField == "") {
                        defaultStateEditText(binding.editText)
                    } else {
                        callback(textField)
                    }
                    true
                }
                else -> false
            }
        }

        builder.setPositiveButton("OK") { dialog, _ ->
            if (textField == "") {
                dialog.dismiss()
            } else {
                callback(textField)
            }
        }
        builder.setNegativeButton("Cancel") { dialog, _ ->
            dialog.cancel()
        }
        return builder.create()
    }

    private fun inflate(builder: AlertDialog.Builder) {
        _binding = DialogFieldEditorBinding.inflate(inflater)
        builder.setView(binding.root)
    }

    private fun defaultStateEditText(editText: EditText) {
        hideSoftKeyboard()
        editText.text!!.clear()
        editText.clearFocus()
        editText.isCursorVisible = false
    }

    companion object {
        const val TAG = "FieldEditor"
    }
}
