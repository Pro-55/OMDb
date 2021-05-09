package com.example.omdb.util

import androidx.room.TypeConverter
import com.example.omdb.models.Type

class RoomTypeConverter {

    @TypeConverter
    fun toContentType(contentType: Type?): Int {
        return contentType?.ordinal ?: 0
    }

}