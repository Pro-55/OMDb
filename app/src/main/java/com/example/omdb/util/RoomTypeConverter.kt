package com.example.omdb.util

import androidx.room.TypeConverter
import com.example.omdb.domain.model.Type

class RoomTypeConverter {

    @TypeConverter
    fun fromContentType(value: Int?): Type {
        return Type.entries[value ?: 0]
    }

    @TypeConverter
    fun toContentType(contentType: Type?): Int {
        return contentType?.ordinal ?: 0
    }

}