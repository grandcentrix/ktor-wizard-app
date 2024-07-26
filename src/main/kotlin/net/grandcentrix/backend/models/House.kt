package net.grandcentrix.backend.models;

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class House (
    val id: String,
    val name: String,
    @SerialName("houseColours")
    val colors: String?,
    val founder: String?,
    val animal: String?,
    val element: String?,
    val ghost: String?,
    val commonRoom: String?,
    val heads: List<Heads>? = mutableListOf(),
    val traits: List<Traits>? = mutableListOf()
)

@Serializable
data class Heads (
    val id: String,
    val firstName: String,
    val lastName: String
)

@Serializable
data class Traits (
    val id: String,
    val name: String
)