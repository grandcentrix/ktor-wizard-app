package net.grandcentrix.backend.models;

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class House (
    val id: String,
    val name: String,
    @SerialName("houseColours")
    val colors: String?,
    val motto: String? = "",
    val founder: String?,
    val animal: String?,
    val element: String?,
    val ghost: String?,
    val commonRoom: String?,
    val heads: List<Head>? = mutableListOf(),
    val traits: List<Trait>? = mutableListOf()
)

@Serializable
data class Head (
    val id: String,
    val firstName: String,
    val lastName: String
) {
    override fun toString(): String {
        return "$firstName $lastName"
    }
}

@Serializable
data class Trait (
    val id: String,
    val name: String
) {
    override fun toString(): String {
        return name
    }
}