package com.example.omdb.data.local.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.omdb.data.local.model.EntityUser

@Dao
interface UserDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(user: EntityUser): Long

    @Query("SELECT * FROM user_table WHERE _id=:id")
    suspend fun get(id: String): EntityUser?

}