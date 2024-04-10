package net.grandcentrix.backend.models;

import java.util.UUID

data class Spell(
    val id: UUID,
    val name: String,
    val category: String,
    val creator: Character,
    val effect: String,
    val hand: String,
    val imageUrl: String,
    val incantation: String,
    val light: String,
    val slug: String,
    val type: String
)