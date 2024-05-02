package net.grandcentrix.backend.models;

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class House (
    val id: String,
    val name: String,
    @SerialName("houseColours")
    val colors: String,
    val founder: String,
    val animal: String,
    val element: String,
    val ghost: String,
    val commonRoom: String,
    val heads: List<Heads>,
    val traits: List<Traits>
)

@Serializable
data class Heads (
    val firstName: String,
    val lastName: String
)

@Serializable
data class Traits (
    val name: String
)