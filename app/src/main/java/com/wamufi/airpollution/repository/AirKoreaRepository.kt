package com.wamufi.airpollution.repository

import com.wamufi.airpollution.api.AirKoreaClient

class AirKoreaRepository {
    private val client = AirKoreaClient.create()

    suspend fun getRealTimeInfo(queries: Map<String, String>) = client.getRealTimeInfo(queries)
}