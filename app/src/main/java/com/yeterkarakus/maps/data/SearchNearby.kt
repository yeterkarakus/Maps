package com.yeterkarakus.maps.data

import com.yeterkarakus.maps.data.Datum

data class SearchNearby(
    val status: String,
    val request_id: String,
    val parameters: Parameters,
    val data: List<Datum>
)
