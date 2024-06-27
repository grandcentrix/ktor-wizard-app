package net.grandcentrix.backend.models

import kotlinx.serialization.Serializable

@Serializable
data class FavouriteItems (
    val books: MutableList<String>? = mutableListOf(),
    val characters: MutableList<String>? = mutableListOf(),
    val houses: MutableList<String>? = mutableListOf(),
    val movies: MutableList<String>? = mutableListOf(),
    val potions: MutableList<String>? = mutableListOf(),
    val spells: MutableList<String>? = mutableListOf()
)