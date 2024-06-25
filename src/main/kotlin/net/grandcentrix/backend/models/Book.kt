package net.grandcentrix.backend.models;

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class Book(
    var id: String? = null,
    val author: String,
    @SerialName("cover")
    val coverUrl: String?,
    val pages: Int,
    @SerialName("release_date")
    val releaseDate: String,
    val summary: String,
    val slug: String,
    val title: String
)