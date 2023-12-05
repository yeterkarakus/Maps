package com.yeterkarakus.maps.data

data class PhotosSample(
    val photo_id: String,
    val photo_url: String,
    val photo_url_large: String? = null,
    val video_thumbnail_url: Any? = null,
    val latitude: Double,
    val longitude: Double,
    val photo_datetime_utc: String,
    val photo_timestamp: Long
)

