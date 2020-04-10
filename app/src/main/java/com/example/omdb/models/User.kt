package com.example.omdb.models

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "user_table")
data class User(

    @PrimaryKey(autoGenerate = false)
    val _id: String = "",

    @ColumnInfo(name = "first_name")
    val firstName: String = "",

    @ColumnInfo(name = "last_name")
    val lastName: String = "",

    val email: String = ""
)