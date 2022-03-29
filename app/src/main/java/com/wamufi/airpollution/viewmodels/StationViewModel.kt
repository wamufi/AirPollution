package com.wamufi.airpollution.viewmodels

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.wamufi.airpollution.data.NearbyMsrstnList
import com.wamufi.airpollution.data.TMStdrCrdnt
import com.wamufi.airpollution.repository.AirKoreaRepository
import kotlinx.coroutines.launch
import org.locationtech.proj4j.CRSFactory
import org.locationtech.proj4j.CoordinateTransformFactory
import org.locationtech.proj4j.ProjCoordinate

class StationViewModel : ViewModel() {
    private val repository = AirKoreaRepository()

    private val _stationsList = MutableLiveData<List<NearbyMsrstnList.Response.Body.Item>>()
    val stationsList: LiveData<List<NearbyMsrstnList.Response.Body.Item>> = _stationsList

    private val _tmList = MutableLiveData<List<TMStdrCrdnt.Response.Body.Item>>()
    val tmList: LiveData<List<TMStdrCrdnt.Response.Body.Item>> = _tmList

    val _coordinate = MutableLiveData<ProjCoordinate>()
    val coordinate: LiveData<ProjCoordinate> = _coordinate

    fun getNearbyStationsList(queries: Map<String, String>) {
        viewModelScope.launch {
            repository.getNearbyStationsList(queries).apply {
                if (isSuccessful) {
                    _stationsList.value = body()?.response?.body?.items
                }
            }
        }
    }

    fun getTMByAddr(queries: Map<String, String>) {
        viewModelScope.launch {
            repository.getTMByAddr(queries).apply {
                if (isSuccessful) {
                    _tmList.value = body()?.response?.body?.items
                }
            }
        }
    }

    /**
     * 좌표 변환 (WGS84 -> GRS80)
     * @url https://www.osgeo.kr/17
     */
    fun transformCoordinate(latitude: Double, longitude: Double) {
        val factory = CRSFactory()
        val WGS84 = factory.createFromName("epsg:4326")
        val GRS80 = factory.createFromName("epsg:5181")

        val transform = CoordinateTransformFactory().createTransform(WGS84, GRS80)
        _coordinate.value = transform.transform(ProjCoordinate(longitude, latitude), ProjCoordinate())
    }
}