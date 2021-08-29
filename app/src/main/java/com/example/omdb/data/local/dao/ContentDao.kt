package com.example.omdb.data.local.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.omdb.models.local.ContentRelation
import com.example.omdb.models.local.EntityContent

@Dao
interface ContentDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(content: EntityContent): Long

    @Query("SELECT * FROM content_table WHERE _id=:id")
    suspend fun get(id: String): ContentRelation?

}