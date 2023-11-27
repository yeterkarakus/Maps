package com.yeterkarakus.maps.room

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query

@Dao
interface SearchDao {
    @Query("SELECT * FROM search")
    suspend fun getAll() : List<Search>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAll(vararg search: Search)

    @Delete
    suspend fun delete(search: Search)
}