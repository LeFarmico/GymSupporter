package com.lefarmico.donetime.views.activities

import com.lefarmico.donetime.R
import com.lefarmico.donetime.databinding.ActivityMainBinding
import com.lefarmico.donetime.viewModels.MainViewModel
import com.lefarmico.donetime.views.base.BaseActivity
import com.lefarmico.donetime.views.fragments.ExerciseListFragment

class MainActivity : BaseActivity<ActivityMainBinding, MainViewModel>(
    ActivityMainBinding::inflate,
    MainViewModel::class.java
) {
    override fun setUpViews() {

        supportFragmentManager.beginTransaction()
            .replace(R.id.fragment, ExerciseListFragment())
            .commit()
    }

    override fun observeView() {
        super.observeView()
    }

    override fun observeData() {
        super.observeData()
    }
}
