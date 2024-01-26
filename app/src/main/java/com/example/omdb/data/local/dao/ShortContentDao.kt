package com.example.omdb.data.local.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.omdb.data.local.model.EntityShortContent
import com.example.omdb.domain.model.Type

@Dao
interface ShortContentDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAll(contents: List<EntityShortContent>): List<Long>

    @Query("SELECT * FROM short_content_table WHERE type=:type AND title LIKE :searchString")
    suspend fun searchForType(type: Type, searchString: String): List<EntityShortContent?>?

}