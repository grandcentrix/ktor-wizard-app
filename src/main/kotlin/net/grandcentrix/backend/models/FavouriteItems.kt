package net.grandcentrix.backend.models

import kotlinx.serialization.Serializable
import org.jetbrains.exposed.sql.Table

@Serializable
data class FavouriteItem (
    val userId: String,
    val bookId: String? = null,
    val characterId: String? = null,
    val houseId: String? = null,
    val movieId: String? = null,
    val potionId: String? = null,
    val spellId: String? = null
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

