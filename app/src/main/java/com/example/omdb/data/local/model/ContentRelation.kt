package com.example.omdb.data.local.model

import androidx.room.Embedded
import androidx.room.Relation
import com.example.omdb.domain.model.Content

data class ContentRelation(

    @Embedded
    val data: EntityContent?,

    @Relation(parentColumn = "_id", entityColumn = "content_id", entity = EntityRating::class)
    val ratings: List<EntityRating>?

)

fun ContentRelation.parse(): Content? = data?.parse(ratings)