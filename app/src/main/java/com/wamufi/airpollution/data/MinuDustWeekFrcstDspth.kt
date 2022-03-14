package com.wamufi.airpollution.data

data class MinuDustWeekFrcstDspth(
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
                val frcstFourCn: String,
                val frcstFourDt: String,
                val frcstOneCn: String,
                val frcstOneDt: String,
                val frcstThreeCn: String,
                val frcstThreeDt: String,
                val frcstTwoCn: String,
                val frcstTwoDt: String,
                val gwthcnd: String,
                val presnatnDt: String
            )
        }

        data class Header(
            val resultCode: String,
            val resultMsg: String
        )
    }
}