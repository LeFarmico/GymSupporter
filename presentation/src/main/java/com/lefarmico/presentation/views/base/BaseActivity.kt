package com.lefarmico.presentation.views.base

import android.os.Bundle
import android.view.LayoutInflater
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import androidx.viewbinding.ViewBinding

typealias LayoutInflate<T> = (LayoutInflater) -> T

abstract class BaseActivity<VB : ViewBinding, VM : BaseViewModel<out BaseIntent>>(
    private val inflate: LayoutInflate<VB>,
    private val provideViewModel: Class<VM>
) : AppCompatActivity(), ISetupBaseActivity {

    private lateinit var viewModel: VM

    internal lateinit var binding: VB

    final override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = inflate(layoutInflater)
        setContentView(binding.root)
        viewModel = ViewModelProvider(this).get(provideViewModel)
        setUpViews()
        observeView()
        observeData()
    }

    override fun setContentView(layoutResID: Int) {
        super.setContentView(layoutResID)
    }

    override fun setUpViews() {}

    override fun observeView() {}

    override fun observeData() {}
}
