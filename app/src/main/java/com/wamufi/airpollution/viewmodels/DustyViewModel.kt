package com.wamufi.airpollution.viewmodels

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.wamufi.airpollution.data.MsrstnAcctoRltmMesureDnsty
import com.wamufi.airpollution.repository.AirKoreaRepository
import kotlinx.coroutines.launch

class DustyViewModel(private val repository: AirKoreaRepository) : ViewModel() {
    private val _realTimeInfo = MutableLiveData<List<MsrstnAcctoRltmMesureDnsty.Response.Body.Item>>()
    val realTimeInfo = _realTimeInfo

    fun getRealTimeInfo(queries: Map<String, String>) {
        viewModelScope.launch {
            Log.v("viewmodel", repository.getRealTimeInfo(queries).toString())
            repository.getRealTimeInfo(queries).apply {
                if (isSuccessful) {
                    _realTimeInfo.value = body()?.response?.body?.items
                }
            }
        }
    }
}