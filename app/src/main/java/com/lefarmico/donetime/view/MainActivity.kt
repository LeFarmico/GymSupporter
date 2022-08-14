package com.lefarmico.donetime.view
import android.os.Bundle
import androidx.lifecycle.LiveData
import androidx.lifecycle.Observer
import androidx.navigation.NavController
import androidx.navigation.ui.setupActionBarWithNavController
import com.lefarmico.core.base.BaseActivity
import com.lefarmico.donetime.R
import com.lefarmico.donetime.databinding.ActivityMainBinding
import com.lefarmico.donetime.navigation.setupWithNavController
import com.lefarmico.navigation.Router
import javax.inject.Inject

class MainActivity : BaseActivity<
    MainIntent, MainState, MainEvent,
    ActivityMainBinding, MainViewModel>(
    ActivityMainBinding::inflate,
    MainViewModel::class.java
) {

    @Inject
    lateinit var router: Router

    private var currentNavController: LiveData<NavController>? = null

    override fun setTitle(titleId: Int) {
        super.setTitle(titleId)
        supportActionBar?.title = title
    }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setSupportActionBar(binding.appToolbar)
        supportActionBar?.elevation = 0f

        if (savedInstanceState == null) {
            setupBottomNavigationBar()
        }
        router.bind(this)
        dispatchIntent(MainIntent.LoadPreloadedData)
    }

    override fun onRestoreInstanceState(savedInstanceState: Bundle) {
        super.onRestoreInstanceState(savedInstanceState)
        setupBottomNavigationBar()
    }

    override fun onSupportNavigateUp(): Boolean {
        return currentNavController?.value?.navigateUp() ?: false
    }

    override fun onResume() {
        super.onResume()
        router.bind(this)
    }

    override fun receive(state: MainState) {}

    override fun receive(event: MainEvent) {
        when (event) {
            MainEvent.LoadDataResult.Failure -> onFailureEvent()
            MainEvent.LoadDataResult.Success -> onLoadSuccess()
        }
    }

    private fun setupBottomNavigationBar() {
        val bottomNavigationView = binding.bottomNavigation
        val navGraphIds = listOf(
            R.navigation.nav_graph,
            R.navigation.nav_graph_exercises,
            R.navigation.nav_graph_settings
        )

        // Setup the bottom navigation view with a list of navigation graphs
        val controller = bottomNavigationView.setupWithNavController(
            navGraphIds = navGraphIds,
            fragmentManager = supportFragmentManager,
            containerId = R.id.fragment,
            intent = intent
        )

        // Whenever the selected controller changes, setup the action bar.
        controller.observe(this) { navController ->
            router.bindNavController(navController)
//            setupActionBarWithNavController(navController)
        }
        currentNavController = controller
    }

    private fun onFailureEvent() {
        dispatchIntent(MainIntent.ShowToast(getString(R.string.load_data_failure)))
    }

    private fun onLoadSuccess() {
        // TODO send analytics
    }
}
