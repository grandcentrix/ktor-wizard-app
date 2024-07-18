package net.grandcentrix.backend.controllers

import getBooksTemplate
import getCharactersTemplate
import getHousesTemplate
import getMoviesTemplate
import getPotionsTemplate
import getSpellsTemplate
import io.ktor.server.application.*
import io.ktor.server.util.*
import net.grandcentrix.backend.dao.daoFavouriteItems
import net.grandcentrix.backend.dao.daoUsers
import net.grandcentrix.backend.models.*
import net.grandcentrix.backend.plugins.DAOUsersException
import net.grandcentrix.backend.plugins.FavouriteItemsException
import net.grandcentrix.backend.plugins.RequestException


suspend fun ApplicationCall.addFavouriteItem(userSession: UserSession) {

    val user = daoUsers.getItem(userSession.username)
        ?: throw RequestException("Not possible to add favourite item - user not found!")

    val item = parameters.getOrFail<String>("item")
    val itemId = parameters.getOrFail<String>("itemId")

    when (item) {
        "books" -> {
            val favouriteBook = BookItem(user.id, bookId = itemId)
            daoFavouriteItems.addFavouriteBook(favouriteBook)

            getBooksTemplate(userSession, item)
        }
        "characters" -> {
            val favouriteCharacter = CharacterItem(user.id, characterId = itemId)
            daoFavouriteItems.addFavouriteCharacter(favouriteCharacter)

            getCharactersTemplate(userSession, item)
        }
        "houses" -> {
            val favouriteHouse = HouseItem(user.id, houseId = itemId)
            daoFavouriteItems.addFavouriteHouse(favouriteHouse)

            getHousesTemplate(userSession, item)
        }
        "movies" -> {
            val favouriteMovie = MovieItem(user.id, movieId = itemId)
            daoFavouriteItems.addFavouriteMovie(favouriteMovie)

            getMoviesTemplate(userSession, item)
        }
        "potions" -> {
            val favouritePotion = PotionItem(user.id, potionId = itemId)
            daoFavouriteItems.addFavouritePotion(favouritePotion)

            getPotionsTemplate(userSession, item)
        }
        "spells" -> {
            val favouriteSpell = SpellItem(user.id, spellId = itemId)
            daoFavouriteItems.addFavouriteSpell(favouriteSpell)

            getSpellsTemplate(userSession, item)
        }
        else -> throw FavouriteItemsException("Failed to favorite item.")
    }
}

fun userFavouriteItems(username: String?, item: String): List<String> {
    if (username == null) {
        return listOf()
    }

    val userId = daoUsers.getItem(username)?.id
        ?: throw DAOUsersException("Database error: Not possible to retrieve user.")

    return when (item) {
        "books" -> {
            daoFavouriteItems.getUserFavouriteBooks(userId)
        }
        "characters" -> {
            daoFavouriteItems.getUserFavouriteCharacters(userId)
        }
        "houses" -> {
            daoFavouriteItems.getUserFavouriteHouses(userId)
        }
        "movies" -> {
            daoFavouriteItems.getUserFavouriteMovies(userId)
        }
        "potions" -> {
            daoFavouriteItems.getUserFavouritePotions(userId)
        }
        "spells" -> {
            daoFavouriteItems.getUserFavouriteSpells(userId)
        }
        else -> {
            return listOf()
        }
    }
}

suspend fun ApplicationCall.removeFavouriteItem(userSession: UserSession) {

    val user = daoUsers.getItem(userSession.username)
        ?: throw RequestException("Not possible to add favourite item - user not found!")

    val item = parameters.getOrFail<String>("item")
    val itemId = parameters.getOrFail<String>("itemId")

    when (item) {
        "books" -> {
            val favouriteBook = BookItem(user.id, bookId = itemId)
            daoFavouriteItems.removeFavouriteBook(favouriteBook)

            getBooksTemplate(userSession, item)
        }
        "characters" -> {
            val favouriteCharacter = CharacterItem(user.id, characterId = itemId)
            daoFavouriteItems.removeFavouriteCharacter(favouriteCharacter)

            getCharactersTemplate(userSession, item)
        }
        "houses" -> {
            val favouriteHouse = HouseItem(user.id, houseId = itemId)
            daoFavouriteItems.removeFavouriteHouse(favouriteHouse)

            getHousesTemplate(userSession, item)
        }
        "movies" -> {
            val favouriteMovie = MovieItem(user.id, movieId = itemId)
            daoFavouriteItems.removeFavouriteMovie(favouriteMovie)

            getMoviesTemplate(userSession, item)
        }
        "potions" -> {
            val favouritePotion = PotionItem(user.id, potionId = itemId)
            daoFavouriteItems.removeFavouritePotion(favouritePotion)

            getPotionsTemplate(userSession, item)
        }
        "spells" -> {
            val favouriteSpell = SpellItem(user.id, spellId = itemId)
            daoFavouriteItems.removeFavouriteSpell(favouriteSpell)

            getSpellsTemplate(userSession, item)
        }
        else -> throw FavouriteItemsException("Failed to favorite item.")
    }
}