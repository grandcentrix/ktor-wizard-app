package net.grandcentrix.backend.models;

import java.util.*

data class Book(
    val id: UUID,
    val author: String,
    val coverUrl: String,
    val pages: Int,
    val releaseDate: Date,
    val summary: String,
    val slug: String,
    val title: String
)