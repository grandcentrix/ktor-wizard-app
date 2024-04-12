package net.grandcentrix.backend.models;

import kotlinx.serialization.Contextual
import kotlinx.serialization.Serializable
import java.util.UUID
@Serializable
data class Potion(
    @Contextual
    val id: UUID,
    val name: String,
    val characteristics: List<String>,
    val difficulty: DifficultyLevels,
    val effect: String,
    val imageUrl: String,
    @Contextual
    val inventors: List<Character>,
    val ingredients: List<String>,
    val manufacturers: List<String>,
    val sideEffects: String,
    val slug: String,
    val time: Int
)