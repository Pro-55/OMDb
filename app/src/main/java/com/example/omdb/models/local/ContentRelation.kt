package com.example.omdb.models.local

import androidx.room.Embedded
import androidx.room.Relation
import com.example.omdb.models.Content

data class ContentRelation(

    @Embedded
    val data: EntityContent?,

    @Relation(parentColumn = "_id", entityColumn = "content_id", entity = EntityRating::class)
    val ratings: List<EntityRating>?

)

fun List<ContentRelation?>.parse(): List<Content> = mapNotNull { it?.parse() }

fun ContentRelation.parse(): Content? = data?.parse(ratings)