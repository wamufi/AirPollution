package com.wamufi.airpollution.data

data class TMStdrCrdnt(
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
                val sggName: String,
                val sidoName: String,
                val tmX: String,
                val tmY: String,
                val umdName: String
            )
        }

        data class Header(
            val resultCode: String,
            val resultMsg: String
        )
    }
}