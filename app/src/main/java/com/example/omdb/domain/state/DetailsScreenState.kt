package com.example.omdb.domain.state

import com.example.omdb.domain.model.Content
import com.example.omdb.domain.model.ShortContent

data class DetailsScreenState(
    val _id: String? = null,
    val shortContent: ShortContent? = null,
    val content: Content? = null
)