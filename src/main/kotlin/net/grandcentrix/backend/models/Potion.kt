package net.grandcentrix.backend.models;

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class Potion(
    var id: String? = null,
    val name: String,
    val characteristics: String?,
    val difficulty: String?,
    val effect: String?,
    val wiki: String,
    @SerialName("image")
    var imageUrl: String?,
    val inventors: String?,
    val ingredients: String?,
    val manufacturers: String?,
    @SerialName("side_effects")
    val sideEffects: String?,
    val slug: String?,
    val time: String?
)