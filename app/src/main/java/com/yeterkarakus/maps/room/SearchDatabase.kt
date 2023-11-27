package com.yeterkarakus.maps.room

import androidx.room.Database
import androidx.room.RoomDatabase

@Database(entities = [Search::class], version = 1)
abstract class SearchDatabase : RoomDatabase() {
    abstract fun searchDao(): SearchDao
}