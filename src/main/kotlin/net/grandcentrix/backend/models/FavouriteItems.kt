package net.grandcentrix.backend.models

import kotlinx.serialization.Serializable
import org.jetbrains.exposed.sql.Table

@Serializable
data class BookItem (
    val userId: String,
    val bookId: String,
)

@Serializable
data class CharacterItem (
    val userId: String,
    val characterId: String,
)

@Serializable
data class HouseItem (
    val userId: String,
    val houseId: String,
)

@Serializable
data class MovieItem (
    val userId: String,
    val movieId: String,
)

@Serializable
data class PotionItem (
    val userId: String,
    val potionId: String,
)

@Serializable
data class SpellItem (
    val userId: String,
    val spellId: String,
)


object FavouriteBooks : Table() {
    val userId = varchar("FK_userID", 128)
        .references(Users.id)
    val bookId = varchar("bookID", 128)

    init {
        index(true, userId, bookId)
    }
}

object FavouriteCharacters : Table() {
    val userId = varchar("FK_userID", 128)
        .references(Users.id)
    val characterId = varchar("characterID", 128)

    init {
        index(true, userId, characterId)
    }
}

object FavouriteHouses : Table() {
    val userId = varchar("FK_userID", 128)
        .references(Users.id)
    val houseId = varchar("houseID", 128)

    init {
        index(true, userId, houseId)
    }
}

object FavouriteMovies : Table() {
    val userId = varchar("FK_userID", 128)
        .references(Users.id)
    val movieId = varchar("movieID", 128)

    init {
        index(true, userId, movieId)
    }
}

object FavouritePotions : Table() {
    val userId = varchar("FK_userID", 128)
        .references(Users.id)
    val potionId = varchar("potionID", 128)

    init {
        index(true, userId, potionId)
    }
}

object FavouriteSpells : Table() {
    val userId = varchar("FK_userID", 128)
        .references(Users.id)
    val spellId = varchar("spellID", 128)

    init {
        index(true, userId, spellId)
    }
}

