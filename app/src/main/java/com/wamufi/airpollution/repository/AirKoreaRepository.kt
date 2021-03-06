package com.wamufi.airpollution.repository

import com.wamufi.airpollution.api.AirKoreaClient

class AirKoreaRepository {
    private val client = AirKoreaClient.create()

    suspend fun getRealTimeInfo(queries: Map<String, String>) = client.getRealTimeInfo(queries)

    suspend fun getForecast(queries: Map<String, String>) = client.getForecast(queries)

    suspend fun getWeekForecast(queries: Map<String, String>) = client.getWeekForecast(queries)

    suspend fun getNearbyStationsList(queries: Map<String, String>) = client.getNearbyStationsList(queries)

    suspend fun getTMByAddr(queries: Map<String, String>) = client.getTMByAddr(queries)
}