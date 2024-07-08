package net.grandcentrix.backend.models;

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class Spell(
    var id: String? = null,
    val name: String,
    val category: String?,
    val creator: String?,
    val effect: String?,
    val hand: String?,
    @SerialName("image")
    var imageUrl: String?,
    val incantation: String?,
    val light: String?,
    val slug: String?,
)