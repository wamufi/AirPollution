package com.wamufi.airpollution

import android.os.Bundle
import android.view.Menu
import androidx.activity.viewModels
import com.google.android.material.snackbar.Snackbar
import com.google.android.material.navigation.NavigationView
import androidx.navigation.findNavController
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.navigateUp
import androidx.navigation.ui.setupActionBarWithNavController
import androidx.navigation.ui.setupWithNavController
import androidx.drawerlayout.widget.DrawerLayout
import androidx.appcompat.app.AppCompatActivity
import com.wamufi.airpollution.databinding.ActivityMainBinding
import com.wamufi.airpollution.viewmodels.DustyViewModel
import java.text.SimpleDateFormat
import java.util.*

class MainActivity : AppCompatActivity() {

    private lateinit var appBarConfiguration: AppBarConfiguration
    private lateinit var binding: ActivityMainBinding

//    private lateinit var viewModelFactory: AirKoreaViewModelFactory
//    private lateinit var viewModel: DustyViewModel
    private val viewModel: DustyViewModel by viewModels()

    private val today: String get() {
        val time = Calendar.getInstance().time
        return SimpleDateFormat("yyyy-MM-dd").format(time)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setSupportActionBar(binding.appBarMain.toolbar)

        binding.appBarMain.fab.setOnClickListener { view ->
            Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                .setAction("Action", null).show()
        }
        val drawerLayout: DrawerLayout = binding.drawerLayout
        val navView: NavigationView = binding.navView
        val navController = findNavController(R.id.nav_host_fragment_content_main)
        // Passing each menu ID as a set of Ids because each
        // menu should be considered as top level destinations.
        appBarConfiguration = AppBarConfiguration(
            setOf(
                R.id.nav_home, R.id.nav_gallery, R.id.nav_slideshow
            ), drawerLayout
        )
        setupActionBarWithNavController(navController, appBarConfiguration)
        navView.setupWithNavController(navController)

        initViewModel()

        getData()
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        // Inflate the menu; this adds items to the action bar if it is present.
        menuInflater.inflate(R.menu.main, menu)
        return true
    }

    override fun onSupportNavigateUp(): Boolean {
        val navController = findNavController(R.id.nav_host_fragment_content_main)
        return navController.navigateUp(appBarConfiguration) || super.onSupportNavigateUp()
    }

    private fun initViewModel() {
//        viewModelFactory = AirKoreaViewModelFactory(AirKoreaRepository())
//        viewModel = ViewModelProvider(this, viewModelFactory)[DustyViewModel::class.java]
    }

    private fun getData() {
        val realTimeMap = mapOf("returnType" to "json", "stationName" to "종로구", "dataTerm" to "DAILY")
        viewModel.getRealTimeInfo(realTimeMap)

        val forecastMap = mapOf("returnType" to "json", "searchDate" to today)
        viewModel.getForecast(forecastMap)

        val weekForecastMap = mapOf("returnType" to "json", "searchDate" to today)
        viewModel.getWeekForecast(weekForecastMap)
    }
}