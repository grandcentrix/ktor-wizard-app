package net.grandcentrix.backend.dao

import net.grandcentrix.backend.models.*
import net.grandcentrix.backend.plugins.DAOUsersException
import org.jetbrains.exposed.sql.insertIgnore
import org.jetbrains.exposed.sql.select
import org.jetbrains.exposed.sql.transactions.transaction

class DAOFavouriteItems {

    fun addFavouriteItem(item: FavouriteItem) = transaction {
        if (item.bookId != null) {
            val insertStatement = FavouriteBooks.insertIgnore { books ->
                books[userId] = item.userId
                books[bookId] = item.bookId
            }
            if (insertStatement.resultedValues?.singleOrNull() == null) {
                throw DAOUsersException("Failed to add favourite item book with ID: ${item.bookId}")
            }
        } else if (item.characterId != null) {
            val insertStatement = FavouriteCharacters.insertIgnore { characters ->
                characters[userId] = item.userId
                characters[characterId] = item.characterId
            }
            if (insertStatement.resultedValues?.singleOrNull() == null) {
                throw DAOUsersException("Failed to add favourite item character with ID: ${item.characterId}")
            }
        } else if (item.houseId != null) {
            val insertStatement = FavouriteHouses.insertIgnore { houses ->
                houses[userId] = item.userId
                houses[houseId] = item.houseId
            }
            if (insertStatement.resultedValues?.singleOrNull() == null) {
                throw DAOUsersException("Failed to add favourite item house with ID: ${item.houseId}")
            }
        } else if (item.movieId != null) {
            val insertStatement = FavouriteMovies.insertIgnore { movies ->
                movies[userId] = item.userId
                movies[movieId] = item.movieId
            }
            if (insertStatement.resultedValues?.singleOrNull() == null) {
                throw DAOUsersException("Failed to add favourite item movie with ID: ${item.movieId}")
            }
        } else if (item.potionId != null) {
            val insertStatement = FavouritePotions.insertIgnore { potions ->
                potions[userId] = item.userId
                potions[potionId] = item.potionId
            }
            if (insertStatement.resultedValues?.singleOrNull() == null) {
                throw DAOUsersException("Failed to add favourite item potion with ID: ${item.potionId}")
            }
        } else if (item.spellId != null) {
            val insertStatement = FavouriteSpells.insertIgnore { spells ->
                spells[userId] = item.userId
                spells[spellId] = item.spellId
            }
            if (insertStatement.resultedValues?.singleOrNull() == null) {
                throw DAOUsersException("Failed to add favourite item spell with ID: ${item.spellId}")
            }
        }
    }

    fun getUserFavouriteBooks(userId: String): List<String> = transaction {
        FavouriteBooks.select { FavouriteBooks.userId eq userId }
            .map { it[FavouriteBooks.bookId] }
    }

    fun getUserFavouriteCharacters(userId: String): List<String> = transaction {
        FavouriteCharacters.select { FavouriteCharacters.userId eq userId }
            .map { it[FavouriteCharacters.characterId] }
    }

    fun getUserFavouriteHouses(userId: String): List<String> = transaction {
        FavouriteHouses.select { FavouriteHouses.userId eq userId }
            .map { it[FavouriteHouses.houseId] }
    }

    fun getUserFavouriteMovies(userId: String): List<String> = transaction {
        FavouriteMovies.select { FavouriteMovies.userId eq userId }
            .map { it[FavouriteMovies.movieId] }
    }

    fun getUserFavouritePotions(userId: String): List<String> = transaction {
        FavouritePotions.select { FavouritePotions.userId eq userId }
            .map { it[FavouritePotions.potionId] }
    }

    fun getUserFavouriteSpells(userId: String): List<String> = transaction {
        FavouriteSpells.select { FavouriteSpells.userId eq userId }
            .map { it[FavouriteSpells.spellId] }
    }
}

val daoFavouriteItems = DAOFavouriteItems()