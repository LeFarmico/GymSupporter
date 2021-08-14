package com.lefarmico.donetime.views.fragments

import com.lefarmico.donetime.databinding.FragmentHomeBinding
import com.lefarmico.donetime.viewModels.HomeViewModel
import com.lefarmico.donetime.views.base.BaseFragment

class HomeFragment : BaseFragment<FragmentHomeBinding, HomeViewModel>(
    FragmentHomeBinding::inflate,
    HomeViewModel::class.java
) {

    override fun setUpViews() {
    }
}
