package net.grandcentrix.backend.models;

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class Potion(
    val id: String = String(), //FIXME
    val name: String,
    val characteristics: List<String>,
    val difficulty: String,
    val effect: String,
    @SerialName("image")
    val imageUrl: String,
    val inventors: List<String>,
    val ingredients: List<String>,
    val manufacturers: List<String>,
    @SerialName("side_effects")
    val sideEffects: String,
    val slug: String,
    val time: String
)