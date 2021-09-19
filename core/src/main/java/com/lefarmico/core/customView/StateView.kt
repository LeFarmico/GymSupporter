package com.lefarmico.core.customView

import android.content.Context
import android.util.AttributeSet
import android.view.LayoutInflater
import android.view.View
import androidx.annotation.Nullable
import androidx.constraintlayout.widget.ConstraintLayout
import com.lefarmico.core.databinding.StateViewBinding

class StateView(
    context: Context,
    @Nullable attrs: AttributeSet
) : ConstraintLayout(context, attrs) {

    private val binding: StateViewBinding =
        StateViewBinding.inflate(LayoutInflater.from(context), this, true)

    private val errorState = binding.error
    private val emptyState = binding.empty
    private val loadingState = binding.loading

    init {
        showSuccessState()
    }

    fun setErrorText(text: String) {
        errorState.text = text
    }

    fun setEmptyText(text: String) {
        emptyState.text = text
    }

    fun showErrorState() {
        errorState.visibility = View.VISIBLE
        emptyState.visibility = View.GONE
        loadingState.visibility = View.GONE
    }

    fun showEmptyState() {
        errorState.visibility = View.GONE
        emptyState.visibility = View.VISIBLE
        loadingState.visibility = View.GONE
    }

    fun showLoadingState() {
        errorState.visibility = View.GONE
        emptyState.visibility = View.GONE
        loadingState.visibility = View.VISIBLE
    }

    fun showSuccessState() {
        errorState.visibility = View.GONE
        emptyState.visibility = View.GONE
        loadingState.visibility = View.GONE
    }
}
