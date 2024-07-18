package net.grandcentrix.backend.models;

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class Movie(
    var id: String? = null,
    val title: String,
    val wiki: String,
    @SerialName("box_office")
    val boxOffice: String?,
    val budget: String?,
    val cinematographers: List<String>?,
    val directors: List<String>?,
    val screenwriters: List<String>?,
    val editors: List<String>?,
    val music_composers: List<String>?,
    val distributors: List<String>?,
    @SerialName("poster")
    val posterUrl: String?,
    val producers: List<String>?,
    val rating: String?,
    @SerialName("release_date")
    val releaseDate: String?,
    val summary: String,
    @SerialName("running_time")
    val duration: String?,
    val slug: String?,
    val trailer: String?
)