package com.lefarmico.core.base

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.ViewModelProvider
import androidx.viewbinding.ViewBinding
import com.lefarmico.core.di.ViewModelFactory
import dagger.android.support.DaggerFragment
import javax.inject.Inject

abstract class BaseFragment<I : BaseIntent, A : BaseAction, S : BaseState.State, E : BaseState.Event,
    VB : ViewBinding,
    VM : BaseViewModel<I, A, S, E>>(
    private val inflate: Inflate<VB>,
    private val provideViewModel: Class<VM>
) : DaggerFragment(), IViewSetup, IViewStateReceiver<S, E> {

    lateinit var viewModel: VM

    @Inject
    lateinit var viewModelFactory: ViewModelFactory

    private var _binding: VB? = null
    val binding get() = _binding!!

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        viewModel = ViewModelProvider(requireActivity(), viewModelFactory)
            .get(provideViewModel)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = inflate.invoke(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setUpViews()
        viewModel.state.observe(viewLifecycleOwner) { state ->
            receive(state)
        }
        viewModel.event.observe(viewLifecycleOwner) { event ->
            receive(event)
        }
        observeData()
        observeView()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    fun dispatchIntent(intent: I) {
        viewModel.dispatchIntent(intent)
    }
    override fun setUpViews() {}
    override fun observeData() {}
    override fun observeView() {}
}
