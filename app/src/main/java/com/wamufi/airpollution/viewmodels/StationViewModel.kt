package com.wamufi.airpollution.viewmodels

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.wamufi.airpollution.data.NearbyMsrstnList
import com.wamufi.airpollution.repository.AirKoreaRepository
import kotlinx.coroutines.launch

class StationViewModel : ViewModel() {
    private val repository = AirKoreaRepository()

    private val _stationsList = MutableLiveData<List<NearbyMsrstnList.Response.Body.Item>>()
    val stationsList: LiveData<List<NearbyMsrstnList.Response.Body.Item>> = _stationsList

    fun getNearbyStationsList(queries: Map<String, String>) {
        viewModelScope.launch {
            repository.getNearbyStationsList(queries).apply {
                if (isSuccessful) {
                    _stationsList.value = body()?.response?.body?.items
                }
            }
        }
    }
}