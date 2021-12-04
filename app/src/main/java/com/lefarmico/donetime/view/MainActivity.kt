package com.lefarmico.donetime.view
import android.os.Bundle
import com.lefarmico.core.base.BaseActivity
import com.lefarmico.donetime.R
import com.lefarmico.donetime.databinding.ActivityMainBinding
import com.lefarmico.navigation.Router
import com.lefarmico.navigation.params.LibraryParams
import com.lefarmico.navigation.screen.Screen
import com.lefarmico.workout.view.WorkoutScreenFragment
import javax.inject.Inject

class MainActivity : BaseActivity<ActivityMainBinding, MainViewModel>(
    ActivityMainBinding::inflate,
    MainViewModel::class.java
) {

    @Inject
    lateinit var router: Router

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setSupportActionBar(binding.appToolbar)
        supportActionBar?.elevation = 0f
        router.bind(this)
        router.bindNavigationUI(binding.bottomNavigation)
    }

    override fun onResume() {
        super.onResume()
        router.bind(this)
    }

    private fun setUpBottomNavigation() {
        binding.bottomNavigation.apply {
            setOnItemSelectedListener {
                when (it.itemId) {
                    R.id.navigation_home -> {
                        router.navigate(Screen.HOME_SCREEN)
                        true
                    }
                    R.id.navigation_exercises -> {
                        router.navigate(Screen.CATEGORY_LIST_SCREEN, LibraryParams.CategoryList(false))
                        true
                    }
                    R.id.navigation_settings -> {
//                        router.show(
//                            Notification.TOAST,
//                            ToastBarParams("Not yet implemented")
//                        )
                        true
                    }
                    else -> false
                }
            }

            setOnItemReselectedListener {
                when (it.itemId) {
                    R.id.navigation_home -> {
                        val currentFragment = supportFragmentManager.findFragmentById(R.id.fragment)
                        if (currentFragment is WorkoutScreenFragment) {
                            router.navigate(Screen.HOME_SCREEN)
                        }
                    }
                }
            }
        }
    }
}
