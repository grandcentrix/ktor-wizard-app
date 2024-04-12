package net.grandcentrix.backend.models;

import kotlinx.serialization.Contextual
import kotlinx.serialization.Serializable
import java.util.UUID
@Serializable
data class Spell(
    @Contextual
    val id: UUID,
    val name: String,
    val category: String,
    @Contextual
    val creator: Character,
    val effect: String,
    val hand: String,
    val imageUrl: String,
    val incantation: String,
    val light: String,
    val slug: String,
    val type: String
)