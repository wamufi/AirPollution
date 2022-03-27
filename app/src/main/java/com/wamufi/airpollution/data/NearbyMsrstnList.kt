package com.wamufi.airpollution.data

/**
 * 근접측정소 목록 조회
 */
data class NearbyMsrstnList(
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
                val addr: String,
                val stationName: String,
                val tm: Double
            )
        }

        data class Header(
            val resultCode: String,
            val resultMsg: String
        )
    }
}