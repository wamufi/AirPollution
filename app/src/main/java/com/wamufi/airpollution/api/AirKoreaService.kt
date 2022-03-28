package com.wamufi.airpollution.api

import com.wamufi.airpollution.data.MinuDustFrcstDspth
import com.wamufi.airpollution.data.MinuDustWeekFrcstDspth
import com.wamufi.airpollution.data.MsrstnAcctoRltmMesureDnsty
import com.wamufi.airpollution.data.NearbyMsrstnList
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.QueryMap

interface AirKoreaService {
    /**
     * 측정소별 실시간 측정정보 조회
     */
    @GET("/B552584/ArpltnInforInqireSvc/getMsrstnAcctoRltmMesureDnsty")
    suspend fun getRealTimeInfo(@QueryMap queries: Map<String, String>): Response<MsrstnAcctoRltmMesureDnsty>

    /**
     * 대기질 예보통보 조회
     */
    @GET("/B552584/ArpltnInforInqireSvc/getMinuDustFrcstDspth")
    suspend fun getForecast(@QueryMap queries: Map<String, String>): Response<MinuDustFrcstDspth>

    /**
     * 초미세먼지 주간예보 조회
     */
    @GET("/B552584/ArpltnInforInqireSvc/getMinuDustWeekFrcstDspth")
    suspend fun getWeekForecast(@QueryMap queries: Map<String, String>): Response<MinuDustWeekFrcstDspth>

    /**
     * 근접측정소 목록 조회
     */
//    @GET("/B552584/MsrstnInfoInqireSvc/getNearbyMsrstnList?&tmX=244148.546388&tmY=412423.75772")
    @GET("/B552584/MsrstnInfoInqireSvc/getNearbyMsrstnList")
    suspend fun getNearbyStationsList(@QueryMap queries: Map<String, String>): Response<NearbyMsrstnList>
}