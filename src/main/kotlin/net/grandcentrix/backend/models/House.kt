package net.grandcentrix.backend.models;

import kotlinx.serialization.Contextual
import kotlinx.serialization.Serializable
import java.util.UUID

@Serializable
data class House(
    @Contextual
    val id: Int,
    val name: String,
    val colors: List<String>,
    @Contextual
    val founder: String,
    val animal: String,
    val element: String,
    val ghost: String,
    val commonRoom: String,
    @Contextual
    val heads: List<Character>,
    val traits: List<String>
)