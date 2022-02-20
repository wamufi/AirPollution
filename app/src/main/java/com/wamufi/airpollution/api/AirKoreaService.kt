package com.wamufi.airpollution.api

import com.wamufi.airpollution.data.MsrstnAcctoRltmMesureDnsty
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.QueryMap

interface AirKoreaService {
    @GET("/B552584/ArpltnInforInqireSvc/getMsrstnAcctoRltmMesureDnsty")
    suspend fun getRealTimeInfo(@QueryMap queries: Map<String, String>): Response<MsrstnAcctoRltmMesureDnsty>
}