package com.wamufi.airpollution.viewmodels

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.wamufi.airpollution.data.MinuDustFrcstDspth
import com.wamufi.airpollution.data.MinuDustWeekFrcstDspth
import com.wamufi.airpollution.data.MsrstnAcctoRltmMesureDnsty
import com.wamufi.airpollution.repository.AirKoreaRepository
import kotlinx.coroutines.launch

class DustyViewModel : ViewModel() {
    private val repository = AirKoreaRepository()

    private val _realTimeInfo = MutableLiveData<List<MsrstnAcctoRltmMesureDnsty.Response.Body.Item>>()
    val realTimeInfo: LiveData<List<MsrstnAcctoRltmMesureDnsty.Response.Body.Item>> = _realTimeInfo

    private val _forecast = MutableLiveData<List<MinuDustFrcstDspth.Response.Body.Item>>()
    val forecast: LiveData<List<MinuDustFrcstDspth.Response.Body.Item>> = _forecast

    private val _weekForecast = MutableLiveData<List<MinuDustWeekFrcstDspth.Response.Body.Item>>()
    val weekForecast: LiveData<List<MinuDustWeekFrcstDspth.Response.Body.Item>> = _weekForecast

    fun getRealTimeInfo(queries: Map<String, String>) {
        viewModelScope.launch {
            repository.getRealTimeInfo(queries).apply {
                if (isSuccessful) {
                    _realTimeInfo.value = body()?.response?.body?.items
                }
            }
        }
    }

    fun getForecast(queries: Map<String, String>) {
        viewModelScope.launch {
            repository.getForecast(queries).apply {
                if (isSuccessful) {
                    _forecast.value = body()?.response?.body?.items
                }
            }
        }
    }

    fun getWeekForecast(queries: Map<String, String>) {
        viewModelScope.launch {
            repository.getWeekForecast(queries).apply {
                if (isSuccessful) {
                    _weekForecast.value = body()?.response?.body?.items
                }
            }
        }
    }
}