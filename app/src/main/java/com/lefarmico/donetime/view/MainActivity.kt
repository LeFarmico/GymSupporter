package com.lefarmico.donetime.view
import android.os.Bundle
import com.lefarmico.core.base.BaseActivity
import com.lefarmico.donetime.R
import com.lefarmico.donetime.databinding.ActivityMainBinding
import com.lefarmico.features.di.MainViewModel
import com.lefarmico.navigation.Router
import com.lefarmico.navigation.notification.Notification
import com.lefarmico.navigation.params.ToastBarParams
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
        router.bind(this)
        setUpBottomNavigation()
    }

    override fun onResume() {
        super.onResume()
        router.bind(this)
    }

    private fun setUpBottomNavigation() {
        binding.bottomNavigation.apply {
            setOnItemSelectedListener {
                when (it.itemId) {
                    R.id.home -> {
                        router.navigate(Screen.HOME_SCREEN)
                        true
                    }
                    R.id.exercises -> {
                        router.navigate(Screen.CATEGORY_SCREEN_FROM_LIBRARY)
                        true
                    }
                    R.id.settings -> {
                        router.show(
                            Notification.TOAST,
                            ToastBarParams("Not yet implemented")
                        )
                        true
                    }
                    else -> false
                }
            }

            setOnItemReselectedListener {
                when (it.itemId) {
                    R.id.home -> {
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
