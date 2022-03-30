package com.wamufi.airpollution

import android.Manifest
import android.annotation.SuppressLint
import android.content.Intent
import android.content.pm.PackageManager
import android.location.LocationManager
import android.os.Build
import android.os.Bundle
import android.provider.Settings
import android.view.Menu
import androidx.activity.result.contract.ActivityResultContracts
import androidx.activity.viewModels
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.drawerlayout.widget.DrawerLayout
import androidx.navigation.findNavController
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.navigateUp
import androidx.navigation.ui.setupActionBarWithNavController
import androidx.navigation.ui.setupWithNavController
import com.google.android.material.navigation.NavigationView
import com.google.android.material.snackbar.Snackbar
import com.wamufi.airpollution.databinding.ActivityMainBinding
import com.wamufi.airpollution.ui.PopupDialogFragment
import com.wamufi.airpollution.utils.Logger
import com.wamufi.airpollution.viewmodels.DustyViewModel
import com.wamufi.airpollution.viewmodels.StationViewModel
import org.locationtech.proj4j.ProjCoordinate
import java.text.SimpleDateFormat
import java.util.*

class MainActivity : AppCompatActivity() {

    private lateinit var appBarConfiguration: AppBarConfiguration
    private lateinit var binding: ActivityMainBinding

    //    private lateinit var viewModelFactory: AirKoreaViewModelFactory
//    private lateinit var viewModel: DustyViewModel
    private val viewModel: DustyViewModel by viewModels()
    private val stationViewModel: StationViewModel by viewModels()

    private val locationManager by lazy {
        getSystemService(LOCATION_SERVICE) as LocationManager
    }

    private val today: String
        get() {
            val time = Calendar.getInstance().time
            return SimpleDateFormat("yyyy-MM-dd").format(time)
        }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setSupportActionBar(binding.appBarMain.toolbar)

        binding.appBarMain.fab.setOnClickListener { view ->
            Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG).setAction("Action", null).show()
        }
        val drawerLayout: DrawerLayout = binding.drawerLayout
        val navView: NavigationView = binding.navView
        val navController = findNavController(R.id.nav_host_fragment_content_main)
        // Passing each menu ID as a set of Ids because each
        // menu should be considered as top level destinations.
        appBarConfiguration = AppBarConfiguration(setOf(R.id.nav_home, R.id.nav_gallery, R.id.nav_slideshow), drawerLayout)
        setupActionBarWithNavController(navController, appBarConfiguration)
        navView.setupWithNavController(navController)

        initViewModel()

        getData()

        getLocationPermission()
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
//        val forecastMap = mapOf("returnType" to "json", "searchDate" to today)
//        viewModel.getForecast(forecastMap)
//
//        val weekForecastMap = mapOf("returnType" to "json", "searchDate" to today)
//        viewModel.getWeekForecast(weekForecastMap)

        stationViewModel.coordinate.observe(this) {
            val stationsListMap = mapOf("returnType" to "json", "tmX" to it.x.toString(), "tmY" to it.y.toString())
            stationViewModel.getNearbyStationsList(stationsListMap)
        }

        stationViewModel.stationsList.observe(this) {
            val realTimeMap = mapOf("returnType" to "json", "stationName" to it[0].stationName, "dataTerm" to "DAILY", "ver" to "1.3")
            viewModel.getRealTimeInfo(realTimeMap)
        }

        val tmMap = mapOf("returnType" to "json", "umdName" to "종로")
        stationViewModel.getTMByAddr(tmMap)
        stationViewModel.tmList.observe(this) {
            val projCoordinate = ProjCoordinate(it[0].tmX.toDouble(), it[0].tmY.toDouble())
            stationViewModel._coordinate.value = projCoordinate
        }
    }

    // 위치 정보 가져오기
    @SuppressLint("MissingPermission")
    private fun getLocation() {
        if (locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER) || locationManager.isProviderEnabled(LocationManager.NETWORK_PROVIDER)) {
            val location = locationManager.getLastKnownLocation(LocationManager.GPS_PROVIDER)
            val networkLocation = locationManager.getLastKnownLocation(LocationManager.NETWORK_PROVIDER)
            Logger.v("gps location: " + location.toString())
            Logger.v("network location: " + networkLocation.toString())
            if (location != null) {
                val latitude = location.latitude
                val longitude = location.longitude
                Logger.v("gps location: $latitude, $longitude")
                stationViewModel.transformCoordinate(latitude, longitude)
            } else if (networkLocation != null) {
                val latitude = networkLocation.latitude
                val longitude = networkLocation.longitude
                Logger.v("network location: $latitude, $longitude")
                stationViewModel.transformCoordinate(latitude, longitude)
            }
        } else {
            Logger.v("gps disabled")
            requestLocationSettings()
        }
    }

    // 위치 설정 화면으로 이동
    private fun requestLocationSettings() {
        val intent = Intent(Settings.ACTION_LOCATION_SOURCE_SETTINGS)
        startActivity(intent)
    }

    // region 권한
    // 권한 확인
    @RequiresApi(Build.VERSION_CODES.M)
    private fun getLocationPermission() {
        when {
            ContextCompat.checkSelfPermission(applicationContext, Manifest.permission.ACCESS_COARSE_LOCATION) == PackageManager.PERMISSION_GRANTED -> { // 권한이 있다면
                Logger.v("gps permission granted")
                getLocation()
            }
            shouldShowRequestPermissionRationale(Manifest.permission.CAMERA) -> { // 권한을 거부한 적이 있다면
                Logger.d("gps permission rationale")
//                showRequestSnackbar()
                val dialog = PopupDialogFragment("현재 위치 획득을 위한 권한 설정이 필요합니다.")
                dialog.show(supportFragmentManager, "PopupDialogFragment")
            }
            else -> {
                Logger.d("gps permission denied or needed")
                requestLocationPermission()
            }
        }
    }

    // 권한 요청
    private fun requestLocationPermission() {
        Logger.d("request gps permission")
        val requestPermissionLauncher = registerForActivityResult(ActivityResultContracts.RequestPermission()) {
            Logger.d("register: $it")
            if (it) { // 권한 허용
                getLocation()
            } else { // 권한 거절
//                showRequestSnackbar()
                val dialog = PopupDialogFragment("현재 위치 획득을 위한 권한 설정이 필요합니다.")
                dialog.show(supportFragmentManager, "PopupDialogFragment")
            }
        }

        requestPermissionLauncher.launch(Manifest.permission.ACCESS_COARSE_LOCATION)
    }

    private fun showRequestSnackbar() {
        val snackbar = Snackbar.make(binding.root, "권한 설정이 필요합니다.", Snackbar.LENGTH_INDEFINITE)
        snackbar.setAction("확인") {
            snackbar.dismiss()
            finish()
        }
        snackbar.show()
    }
    // endregion
}