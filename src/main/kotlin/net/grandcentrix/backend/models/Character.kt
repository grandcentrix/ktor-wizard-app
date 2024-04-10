package net.grandcentrix.backend.models;

import java.util.*

data class Character(
    val id: UUID,
    val name: String,
    val aliasName: String,
    val animagus: String,
    val boggart: String,
    val patronus: String,
    val birthDate: Date,
    val birthPlace: String,
    val deathDate: Date,
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