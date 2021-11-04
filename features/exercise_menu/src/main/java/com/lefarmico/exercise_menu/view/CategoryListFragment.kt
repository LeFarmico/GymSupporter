package com.lefarmico.exercise_menu.view

import android.os.Bundle
import android.os.Parcelable
import androidx.recyclerview.widget.DividerItemDecoration
import com.lefarmico.core.BuildConfig
import com.lefarmico.core.adapter.ExerciseLibraryAdapter
import com.lefarmico.core.base.BaseFragment
import com.lefarmico.core.entity.LibraryViewData
import com.lefarmico.core.utils.EmptyTextValidator
import com.lefarmico.domain.utils.DataState
import com.lefarmico.exercise_menu.databinding.FragmentCategoriesBinding
import com.lefarmico.exercise_menu.intent.CategoryListIntent
import com.lefarmico.exercise_menu.intent.CategoryListIntent.*
import com.lefarmico.exercise_menu.viewModel.CategoryListViewModel
import com.lefarmico.navigation.params.LibraryParams
import java.lang.IllegalArgumentException

class CategoryListFragment : BaseFragment<FragmentCategoriesBinding, CategoryListViewModel>(
    FragmentCategoriesBinding::inflate,
    CategoryListViewModel::class.java
) {

    private val params: LibraryParams.CategoryList by lazy {
        arguments?.getParcelable(KEY_PARAMS)
            ?: LibraryParams.CategoryList(isFromWorkoutScreen = false)
    }

    private val adapter = ExerciseLibraryAdapter().apply {
        onClick = { item ->
            item as LibraryViewData.Category
            startEvent(
                GoToSubcategoryScreen(item.id, params.isFromWorkoutScreen)
            )
        }
    }

    override fun setUpViews() {
        startEvent(GetCategories)

        binding.apply {
            editText.addTextChangedListener(
                EmptyTextValidator(editText, textField)
            )
            recycler.adapter = adapter
            plusButton.setOnClickListener {
                textField.apply {
                    val text = editText?.text.toString()
                    startEvent(AddCategory(text))
                }
            }
            if (recycler.itemDecorationCount == 0) {
                val decorator = DividerItemDecoration(context, DividerItemDecoration.VERTICAL)
                recycler.addItemDecoration(decorator)
            }
        }
    }

    override fun observeData() {
        viewModel.notificationLiveData.observe(viewLifecycleOwner) { notification ->
            startEvent(ShowToast(notification))
        }
        viewModel.categoriesLiveData.observe(viewLifecycleOwner) { dataState ->
            when (dataState) {
                DataState.Empty -> {
                    adapter.items = mutableListOf()
                    binding.state.showEmptyState()
                }
                is DataState.Error -> {
                    adapter.items = mutableListOf()
                    binding.state.showErrorState()
                }
                DataState.Loading -> {
                    adapter.items = mutableListOf()
                    binding.state.showLoadingState()
                }
                is DataState.Success -> {
                    adapter.items = dataState.data
                    binding.state.showSuccessState()
                }
            }
        }
    }

    private fun startEvent(event: CategoryListIntent) {
        viewModel.onTriggerEvent(event)
    }

    companion object {
        private const val KEY_PARAMS = "category_key"

        fun createBundle(data: Parcelable?): Bundle {
            return Bundle().apply {
                when (data) {
                    is LibraryParams.CategoryList -> putParcelable(KEY_PARAMS, data)
                    else -> {
                        if (BuildConfig.DEBUG) {
                            throw (
                                IllegalArgumentException(
                                    "data should be LibraryParams.CategoryList type." +
                                        "but it's type ${data!!.javaClass.canonicalName}"
                                )
                                )
                        }
                    }
                }
            }
        }
    }
}
