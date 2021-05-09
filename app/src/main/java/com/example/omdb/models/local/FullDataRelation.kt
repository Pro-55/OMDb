package com.example.omdb.models.local

import androidx.room.Embedded
import androidx.room.Relation
import com.example.omdb.models.FullData

data class FullDataRelation(

    @Embedded
    val data: EntityFullData?,

    @Relation(parentColumn = "_id", entityColumn = "content_id", entity = EntityRating::class)
    val ratings: List<EntityRating>?

)

fun FullDataRelation.parse(): FullData? = data?.parse(ratings)