package com.yeterkarakus.maps.data.searchdata

enum class Timezone(val value: String) {
    EuropeIstanbul("Europe/Istanbul");

    companion object {
        public fun fromValue(value: String): Timezone = when (value) {
            "Europe/Istanbul" -> EuropeIstanbul
            else              -> throw IllegalArgumentException()
        }
    }
}
