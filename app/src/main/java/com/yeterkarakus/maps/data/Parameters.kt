package com.yeterkarakus.maps.data

data class Parameters (
    val query: String,
    val language: String,
    val region: String,
    val lat: Double,
    val lng: Double,
    val limit: Long
)

