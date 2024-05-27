package net.grandcentrix.backend.models;

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class Character(
    val id: String = String(), //FIXME
    val name: String,
    @SerialName("alias_names")
    val aliasNames: List<String>,
    val animagus: String,
    val boggart: String,
    val patronus: String,
    @SerialName("born")
    val birth: String,
    @SerialName("died")
    val death: String,
    @SerialName("family_members")
    val familyMembers: List<String>,
    val house: House?,
    @SerialName("image")
    val imageUrl: String,
    val jobs: List<String>,
    val nationality: String,
    val slug: String,
    val specie: String,
    val titles: List<String>,
    val wands: List<String>,
)