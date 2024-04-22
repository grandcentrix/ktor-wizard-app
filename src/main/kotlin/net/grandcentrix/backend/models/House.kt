package net.grandcentrix.backend.models;

import kotlinx.serialization.Contextual
import kotlinx.serialization.Serializable

@Serializable
data class House(
    @Contextual
    val id: String,
    val name: String,
    val colors: String,
    val founder: String,
    val animal: String,
    val element: String,
    val ghost: String,
    val commonRoom: String,
    @Contextual
    val heads: List<String>,
    val traits: List<String>
)