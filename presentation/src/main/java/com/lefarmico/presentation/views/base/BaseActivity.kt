package com.lefarmico.presentation.views.base

import android.os.Bundle
import android.view.LayoutInflater
import androidx.lifecycle.ViewModelProvider
import androidx.viewbinding.ViewBinding
import com.lefarmico.presentation.di.viewModel.ViewModelFactory
import dagger.android.support.DaggerAppCompatActivity
import javax.inject.Inject

typealias LayoutInflate<T> = (LayoutInflater) -> T

abstract class BaseActivity<VB : ViewBinding, VM : BaseViewModel<out BaseIntent>>(
    private val inflate: LayoutInflate<VB>,
    private val provideViewModel: Class<VM>
) : DaggerAppCompatActivity(), ISetupBaseActivity {

    private lateinit var viewModel: VM

    @Inject
    lateinit var viewModelFactory: ViewModelFactory

    internal lateinit var binding: VB

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = inflate(layoutInflater)
        setContentView(binding.root)
        viewModel = ViewModelProvider(this, viewModelFactory).get(provideViewModel)
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
