package com.lefarmico.core.base

import android.os.Bundle
import androidx.lifecycle.ViewModelProvider
import androidx.viewbinding.ViewBinding
import com.lefarmico.core.di.ViewModelFactory
import dagger.android.support.DaggerAppCompatActivity
import javax.inject.Inject

abstract class BaseActivity<I : BaseIntent, S : BaseState.State, E : BaseState.Event,
    VB : ViewBinding,
    VM : BaseViewModel<I, S, E>>(
    private val inflate: LayoutInflate<VB>,
    private val provideViewModel: Class<VM>
) : DaggerAppCompatActivity(), IViewStateReceiver<S, E> {

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

    fun dispatchIntent(intent: I) {
        viewModel.dispatchIntent(intent)
    }
}
