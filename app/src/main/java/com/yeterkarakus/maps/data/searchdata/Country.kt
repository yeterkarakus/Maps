package com.yeterkarakus.maps.data.searchdata

enum class Country(val value: String) {
    Tr("TR");

    companion object {
        public fun fromValue(value: String): Country = when (value) {
            "TR" -> Tr
            else -> throw IllegalArgumentException()
        }
    }
}