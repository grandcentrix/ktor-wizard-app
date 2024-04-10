package net.grandcentrix.backend.models;

import java.util.UUID

data class Potion(
    val id: UUID,
    val name: String,
    val characteristics: List<String>,
    val difficulty: DifficultyLevels,
    val effect: String,
    val imageUrl: String,
    val inventors: List<Character>,
    val ingredients: List<String>,
    val manufacturers: List<String>,
    val sideEffects: String,
    val slug: String,
    val time: Int
)