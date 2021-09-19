package com.lefarmico.donetime.view
import android.os.Bundle
import androidx.fragment.app.Fragment
import com.lefarmico.core.base.BaseActivity
import com.lefarmico.donetime.R
import com.lefarmico.donetime.databinding.ActivityMainBinding
import com.lefarmico.features.di.MainViewModel

class MainActivity : BaseActivity<ActivityMainBinding, MainViewModel>(
    ActivityMainBinding::inflate,
    MainViewModel::class.java
) {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setSupportActionBar(binding.appToolbar)
    }
    override fun setUpViews() {
        // TODO navigation module
//        launchFragment(com.lefarmico.home.views.HomeFragment::class.java)
        binding.bottomNavigation.apply {
            setOnItemSelectedListener {
                when (it.itemId) {
                    R.id.home -> {
                        // TODO navigation module
//                        launchFragment(com.lefarmico.home.views.HomeFragment::class.java)
                        true
                    }
                    R.id.exercises -> {
                        // TODO navigation module
//                        launchFragment(com.lefarmico.exercise_library.view.LibraryCategoryFragment::class.java)
                        true
                    }
                    R.id.settings -> { false }
                    else -> false
                }
            }

            setOnItemReselectedListener {
                when (it.itemId) {
                    R.id.home -> {
                        // TODO navigation module
//                        val currentFragment = supportFragmentManager.findFragmentById(R.id.fragment)
//                        if (currentFragment is com.lefarmico.workout.view.WorkoutScreenFragment) {
//                            launchFragment(com.lefarmico.home.views.HomeFragment::class.java)
//                        }
                    }
                }
            }
        }
    }

    override fun observeView() {
    }

    override fun observeData() {
        super.observeData()
    }

    private fun <T : Fragment> launchFragment(fragment: Class<T>, bundle: Bundle? = null) {
        supportFragmentManager.beginTransaction()
            .replace(R.id.fragment, fragment, bundle)
            .addToBackStack(null)
            .commit()
    }
}
