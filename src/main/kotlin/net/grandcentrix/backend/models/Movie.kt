package net.grandcentrix.backend.models;

import kotlinx.serialization.Serializable

@Serializable
data class Movie(
    val id: String,
    val title: String,
    val boxOffice: String,
    val budget: String,
    val cinematographers: List<String>,
    val directors: List<String>,
    val screenwriters: List<String>,
//    val distributors: List<String>,
//    val editors: List<String>,
//    val musicComposers: List<String>,
    val posterUrl: String,
    val producers: List<String>,
    val rating: String,
    val releaseDate: String,
    val duration: String,
    val slug: String,
    val trailer: String
)