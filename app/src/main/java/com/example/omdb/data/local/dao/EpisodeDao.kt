package com.example.omdb.data.local.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.omdb.data.local.model.EntityEpisode

@Dao
interface EpisodeDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAll(episodes: List<EntityEpisode>): List<Long>

    @Query("SELECT * FROM episode_table WHERE content_id=:id AND season=:season")
    suspend fun get(id: String, season: Int): List<EntityEpisode>?

}