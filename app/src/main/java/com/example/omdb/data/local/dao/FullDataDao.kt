package com.example.omdb.data.local.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.omdb.models.local.EntityFullData
import com.example.omdb.models.local.FullDataRelation

@Dao
interface FullDataDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(data: EntityFullData): Long

    @Query("SELECT * FROM full_data_table WHERE _id=:id")
    suspend fun get(id: String): FullDataRelation?

}