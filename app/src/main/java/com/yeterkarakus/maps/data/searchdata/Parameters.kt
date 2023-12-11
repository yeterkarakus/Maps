package com.yeterkarakus.maps.data.searchdata

data class Parameters (
    val query: String,
    val language: String,
    val region: String,
    val lat: Double,
    val lng: Double,
    val limit: Long
)

