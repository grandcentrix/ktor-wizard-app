package net.grandcentrix.backend.models;

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class Book(
    var id: String? = null,
    val author: String,
    val wiki: String,
    val dedication: String,
    @SerialName("cover")
    var coverUrl: String?,
    val pages: Int,
    @SerialName("release_date")
    val releaseDate: String,
    val summary: String,
    val slug: String,
    val title: String,
    val chapters: List<Chapter> = emptyList()
)


@Serializable
data class ChapterDataItem(
    val id: String,
    val type: String,
    val attributes: Chapter
)

@Serializable
data class Chapter(
    val title: String,
    val summary: String?,
)

