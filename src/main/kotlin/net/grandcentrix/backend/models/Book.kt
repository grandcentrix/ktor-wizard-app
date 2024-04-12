package net.grandcentrix.backend.models;

import kotlinx.serialization.Contextual
import kotlinx.serialization.Serializable
import java.util.*

@Serializable
data class Book(
    @Contextual
    val id: UUID,
    val author: String,
    val coverUrl: String,
    val pages: Int,
    @Contextual
    val releaseDate: Date,
    val summary: String,
    val slug: String,
    val title: String
)