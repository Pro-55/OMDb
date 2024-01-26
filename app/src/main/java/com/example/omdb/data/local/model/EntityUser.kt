package com.example.omdb.data.local.model

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.example.omdb.domain.model.User

@Entity(tableName = "user_table")
data class EntityUser(

    @PrimaryKey(autoGenerate = false)
    val _id: String,

    @ColumnInfo(name = "first_name")
    val firstName: String,

    @ColumnInfo(name = "last_name")
    val lastName: String,

    val email: String,

    val profileUrl: String?
)

fun EntityUser.parse(): User = User(
    _id = _id,
    firstName = firstName,
    lastName = lastName,
    email = email,
    profileUrl = profileUrl
)