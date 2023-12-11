package com.yeterkarakus.maps.data.searchdata




data class SearchNearby(
    val status: String,
    val request_id: String,
    val parameters: Parameters,
    val data: List<Datum>
)
