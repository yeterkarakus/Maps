package com.yeterkarakus.maps.data.rewievsdata

import com.yeterkarakus.maps.data.searchdata.Parameters

data class Reviews(
    val status: String,
    val request_id: String,
    val parameters: Parameters,
    val data: List<DatumReviews>
)
