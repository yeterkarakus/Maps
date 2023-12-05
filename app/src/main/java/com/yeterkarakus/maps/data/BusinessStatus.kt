package com.yeterkarakus.maps.data

enum class BusinessStatus(val value: String) {
    Open("OPEN");

    companion object {
        public fun fromValue(value: String): BusinessStatus = when (value) {
            "OPEN" -> Open
            else   -> throw IllegalArgumentException()
        }
    }
}