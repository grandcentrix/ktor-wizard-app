package net.grandcentrix.backend.models;

import java.util.UUID

data class House(
    val id: UUID,
    val name: String,
    val colors: List<String>,
    val founder: Character,
    val animal: String,
    val element: String,
    val ghost: String,
    val commonRoom: String,
    val heads: List<Character>,
    val traits: List<String>
)