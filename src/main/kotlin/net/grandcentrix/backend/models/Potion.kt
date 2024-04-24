package net.grandcentrix.backend.models;

import kotlinx.serialization.Serializable

@Serializable
data class Potion(
    val id: String,
    val name: String,
    val characteristics: List<String>,
    val difficulty: String,
    val effect: String,
    val imageUrl: String,
    val inventors: List<String>,
    val ingredients: List<String>,
    val manufacturers: List<String>,
    val sideEffects: String,
    val slug: String,
    val time: String
)