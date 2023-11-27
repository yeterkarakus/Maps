package com.yeterkarakus.maps.room

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class Search (
    @PrimaryKey(autoGenerate = true)  val searchId :Int = 0 ,
    @ColumnInfo (name = "search_text") val searchText : String
)