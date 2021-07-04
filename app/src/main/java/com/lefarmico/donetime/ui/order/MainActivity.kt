package com.lefarmico.donetime.ui.order
import com.lefarmico.donetime.databinding.ActivityMainBinding
import com.lefarmico.donetime.ui.base.BaseActivity

class MainActivity : BaseActivity<ActivityMainBinding, OrderViewModel>(
    { ActivityMainBinding.inflate(it) },
    OrderViewModel::class.java
) {
    override fun setUpViews() {
        super.setUpViews()
    }

    override fun observeView() {
        super.observeView()
    }

    override fun observeData() {
        super.observeData()
    }
}
