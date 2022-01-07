package com.lefarmico.core.base

import android.os.Bundle
import androidx.lifecycle.ViewModelProvider
import androidx.viewbinding.ViewBinding
import com.lefarmico.core.di.ViewModelFactory
import dagger.android.support.DaggerAppCompatActivity
import javax.inject.Inject

abstract class BaseActivity<I : BaseIntent, A : BaseAction, S : BaseState.State, E : BaseState.Event,
    VB : ViewBinding,
    VM : BaseViewModel<I, A, S, E>>(
    private val inflate: LayoutInflate<VB>,
    private val provideViewModel: Class<VM>
) : DaggerAppCompatActivity() {

    private lateinit var viewModel: VM

    @Inject
    lateinit var viewModelFactory: ViewModelFactory

    lateinit var binding: VB

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = inflate(layoutInflater)
        setContentView(binding.root)
        viewModel = ViewModelProvider(this, viewModelFactory).get(provideViewModel)
    }

    override fun setContentView(layoutResID: Int) {
        super.setContentView(layoutResID)
    }
}
