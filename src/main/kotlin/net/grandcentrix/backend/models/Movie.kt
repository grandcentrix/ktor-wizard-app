package net.grandcentrix.backend.models;

import java.util.Date
import java.util.UUID

data class Movie(
    val id: UUID,
    val title: String,
    val boxOffice: Double,
    val budget: Double,
    val cinematographers: List<String>,
    val directors: List<String>,
    val screenwriters: List<String>,
//    val distributors: List<String>,
//    val editors: List<String>,
//    val musicComposers: List<String>,
    val posterUrl: String,
    val producers: List<String>,
    val rating: String,
    val releaseDate: Date,
    val duration: Int,
    val slug: String,
    val trailer: String
)