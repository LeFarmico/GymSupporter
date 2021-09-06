package com.lefarmico.presentation.views.activities
import android.os.Bundle
import androidx.fragment.app.Fragment
import com.lefarmico.presentation.R
import com.lefarmico.presentation.databinding.ActivityMainBinding
import com.lefarmico.presentation.viewModels.MainViewModel
import com.lefarmico.presentation.views.base.BaseActivity
import com.lefarmico.presentation.views.fragments.HomeFragment
import com.lefarmico.presentation.views.fragments.WorkoutScreenFragment
import com.lefarmico.presentation.views.fragments.listMenu.library.LibraryCategoryFragment

class MainActivity : BaseActivity<ActivityMainBinding, MainViewModel>(
    ActivityMainBinding::inflate,
    MainViewModel::class.java
) {
    override fun setUpViews() {

        launchFragment(HomeFragment::class.java)
        binding.bottomNavigation.apply {
            setOnItemSelectedListener {
                when (it.itemId) {
                    R.id.home -> {
                        launchFragment(HomeFragment::class.java)
                        true
                    }
                    R.id.exercises -> {
                        launchFragment(LibraryCategoryFragment::class.java)
                        true
                    }
                    R.id.settings -> { false }
                    else -> false
                }
            }

            setOnItemReselectedListener {
                when (it.itemId) {
                    R.id.home -> {
                        val currentFragment = supportFragmentManager.findFragmentById(R.id.fragment)
                        if (currentFragment is WorkoutScreenFragment) {
                            launchFragment(HomeFragment::class.java)
                        }
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
