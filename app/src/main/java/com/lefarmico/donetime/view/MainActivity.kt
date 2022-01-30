package com.lefarmico.donetime.view
import android.os.Bundle
import com.lefarmico.core.base.BaseActivity
import com.lefarmico.donetime.R
import com.lefarmico.donetime.databinding.ActivityMainBinding
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

    override fun setTitle(titleId: Int) {
        super.setTitle(titleId)
        supportActionBar?.title = title
    }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setSupportActionBar(binding.appToolbar)
        supportActionBar?.elevation = 0f
        router.bind(this)
        router.bindNavigationUI(binding.bottomNavigation)

        dispatchIntent(MainIntent.LoadPreloadedData)
    }

    override fun onSupportNavigateUp(): Boolean {
        onBackPressed()
        return true
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

    private fun onFailureEvent() {
        dispatchIntent(MainIntent.ShowToast(getString(R.string.load_data_failure)))
    }

    private fun onLoadSuccess() {
        // TODO send analytics
    }
}
