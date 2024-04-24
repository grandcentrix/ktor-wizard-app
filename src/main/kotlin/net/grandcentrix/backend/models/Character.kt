package net.grandcentrix.backend.models;

import kotlinx.serialization.Serializable

@Serializable
data class Character(
    val id: String,
    val name: String,
    val aliasName: String,
    val animagus: String,
    val boggart: String,
    val patronus: String,
    val birth: String,
    val death: String,
    val familyMembers: List<String>,
    val house: House?,
    val imageUrl: String,
    val jobs: List<String>,
    val nationality: String,
    val slug: String,
    val specie: String,
    val titles: List<String>,
    val wands: List<String>,
)