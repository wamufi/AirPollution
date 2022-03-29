package com.wamufi.airpollution.data

/**
 * 측정소별 실시간 측정정보 조회
 */
data class MsrstnAcctoRltmMesureDnsty(
    val response: Response
) {
    data class Response(
        val body: Body,
        val header: Header
    ) {
        data class Body(
            val items: List<Item>,
            val numOfRows: Int,
            val pageNo: Int,
            val totalCount: Int
        ) {
            data class Item(
                val coFlag: Any?, // 일산화탄소 플래그 (측정 자료 상태 정보)
                val coGrade: String, // 일산화탄소 지수
                val coValue: String, // 일산화탄소 농도
                val dataTime: String, // 측정일
                val khaiGrade: String, // 통합 대기 환경 지수
                val khaiValue: String, // 통합 대기 환경 수치
                val mangName: String, // 측정망 정보
                val no2Flag: Any?, // 이산화질소 플래그 (측정 자료 상태 정보)
                val no2Grade: String, // 이산화질소 지수
                val no2Value: String, // 이산화질소 농도
                val o3Flag: Any?, // 오존 플래그 (측정 자료 상태 정보)
                val o3Grade: String, // 오존 지수
                val o3Value: String, // 오존 농도
                val pm10Flag: Any?, // 미세먼지 플래그 (측정 자료 상태 정보)
                val pm10Grade: String, // 미세먼지 지수
                val pm10Grade1h: String, // 미세먼지 1시간 등급
                val pm10Value: String, // 미세먼지 농도
                val pm10Value24: String, // 미세먼지 24시간 등급
                val pm25Flag: Any?, // 초미세먼지 플래그 (측정 자료 상태 정보)
                val pm25Grade: String, // 초미세먼지 지수
                val pm25Grade1h: String, // 초미세먼지 1시간 등급
                val pm25Value: String, // 초미세먼지 농도
                val pm25Value24: String, // 초미세먼지 24시간 등급
                val so2Flag: Any?, // 아황산가스 플래그 (측정 자료 상태 정보)
                val so2Grade: String, // 아황산가스 지수
                val so2Value: String // 아황산가스 농도
            )
        }

        data class Header(
            val resultCode: String,
            val resultMsg: String
        )
    }
}