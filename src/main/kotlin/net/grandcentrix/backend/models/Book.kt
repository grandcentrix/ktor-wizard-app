package net.grandcentrix.backend.models;

import kotlinx.serialization.Serializable

@Serializable
data class Book(
    val id: String,
    val author: String,
    val coverUrl: String,
    val pages: Int,
    val releaseDate: String,
    val summary: String,
    val slug: String,
    val title: String
)