package net.grandcentrix.backend.models;

import kotlinx.serialization.Contextual
import kotlinx.serialization.Serializable
import java.util.*

@Serializable
data class Character(
    @Contextual
    val id: UUID,
    val name: String,
    val aliasName: String,
    val animagus: String,
    val boggart: String,
    val patronus: String,
    @Contextual
    val birthDate: Date,
    val birthPlace: String,
    @Contextual
    val deathDate: Date,
    @Contextual
    val deathPlace: Date,
//    val eyeColor: String,
    val familyMembers: List<Character>,
//    val hairColor: String,
//    val height: Int
    val house: House,
    val imageUrl: String,
    val jobs: List<String>,
    val nationality: String,
    val slug: String,
    val specie: String,
    val titles: List<String>,
    val wands: List<String>,
)