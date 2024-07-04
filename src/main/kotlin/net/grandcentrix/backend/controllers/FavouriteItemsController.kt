package net.grandcentrix.backend.controllers

import io.ktor.http.*
import io.ktor.server.application.*
import io.ktor.server.response.*
import io.ktor.server.sessions.*
import io.ktor.server.util.*
import net.grandcentrix.backend.dao.daoFavouriteItems
import net.grandcentrix.backend.dao.daoUsers
import net.grandcentrix.backend.models.*
import net.grandcentrix.backend.plugins.DAOUsersException
import net.grandcentrix.backend.plugins.FavouriteItemsException
import net.grandcentrix.backend.plugins.RequestException
import net.grandcentrix.backend.plugins.UnauthorizedException

suspend fun ApplicationCall.addFavouriteItem() {

    val item = parameters.getOrFail<String>("item")
    val itemId = parameters.getOrFail<String>("itemId")
    val username = sessions.get<UserSession>()?.username
        ?: throw UnauthorizedException("Username for session not found.")

    val user = daoUsers.getItem(username)
        ?: throw RequestException("Not possible to add favourite item - user not found!")

    when (item) {
        "books" -> {
            val favouriteBook = BookItem(userId = user.id, bookId = itemId)
            daoFavouriteItems.addFavouriteBook(favouriteBook)
        }
        "characters" -> {
            val favouriteCharacter = CharacterItem(userId = user.id, characterId = itemId)
            daoFavouriteItems.addFavouriteCharacter(favouriteCharacter)
        }
        "houses" -> {
            val favouriteHouse = HouseItem(userId = user.id, houseId = itemId)
            daoFavouriteItems.addFavouriteHouse(favouriteHouse)
        }
        "movies" -> {
            val favouriteMovie = MovieItem(userId = user.id, movieId = itemId)
            daoFavouriteItems.addFavouriteMovie(favouriteMovie)
        }
        "potions" -> {
            val favouritePotion = PotionItem(userId = user.id, potionId = itemId)
            daoFavouriteItems.addFavouritePotion(favouritePotion)
        }
        "spells" -> {
            val favouriteSpell = SpellItem(userId = user.id, spellId = itemId)
            daoFavouriteItems.addFavouriteSpell(favouriteSpell)
        }
        else -> throw FavouriteItemsException("Failed to favorite item.")
    }

    respond(HttpStatusCode.OK)
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

        else -> {
            return listOf()
        }
    }
}

fun removeFavouriteItem(item: String, itemId: String, username: String) {
    val user = daoUsers.getItem(username)
        ?: throw RequestException("Not possible to add favourite item - user not found!")

    when (item) {
        "books" -> {
            val favouriteBook = BookItem(userId = user.id, bookId = itemId)
            daoFavouriteItems.addFavouriteBook(favouriteBook)
        }
    }
}
