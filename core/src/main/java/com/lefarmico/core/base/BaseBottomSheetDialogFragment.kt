package com.lefarmico.core.base

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.ViewModelProvider
import androidx.viewbinding.ViewBinding
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import com.lefarmico.core.di.ViewModelFactory
import dagger.android.AndroidInjector
import dagger.android.DispatchingAndroidInjector
import dagger.android.HasAndroidInjector
import dagger.android.support.AndroidSupportInjection
import javax.inject.Inject

abstract class BaseBottomSheetDialogFragment<
    I : BaseIntent, A : BaseAction, S : BaseState.State, E : BaseState.Event,
    VB : ViewBinding,
    VM : BaseViewModel<I, A, S, E>>(
    private val inflate: Inflate<VB>,
    private val provideViewModel: Class<VM>
) : BottomSheetDialogFragment(), IViewSetup, IViewStateReceiver<S, E>, HasAndroidInjector {

    lateinit var viewModel: VM

    @Inject
    lateinit var viewModelFactory: ViewModelFactory

    @Inject
    lateinit var androidInjector: DispatchingAndroidInjector<Any>

    private var _binding: VB? = null
    val binding get() = _binding!!

    override fun androidInjector(): AndroidInjector<Any> {
        return androidInjector
    }

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
        observeData()
        observeView()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    override fun onAttach(context: Context) {
        AndroidSupportInjection.inject(this)
        super.onAttach(context)
    }

    fun dispatchIntent(intent: I) {
        viewModel.dispatchIntent(intent)
    }

    override fun setUpViews() {}
    override fun observeView() {}
    override fun observeData() {}
}
