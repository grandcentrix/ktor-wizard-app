package net.grandcentrix.backend.controllers

import net.grandcentrix.backend.dao.daoFavouriteItems
import net.grandcentrix.backend.dao.daoUsers
import net.grandcentrix.backend.models.FavouriteItem
import net.grandcentrix.backend.plugins.DAOUsersException
import net.grandcentrix.backend.plugins.RequestException

fun addFavouriteItem(item: String, itemId: String, username: String) {

    val user = daoUsers.getItem(username)
        ?: throw RequestException("Not possible to add favourite item - user not found!")

    when (item) {
        "books" -> {
            val favouriteItem = FavouriteItem(userId = user.id, bookId = itemId)
            daoFavouriteItems.addFavouriteItem(favouriteItem)
        }
        "characters" -> {
            val favouriteItem = FavouriteItem(userId = user.id, characterId = itemId)
            daoFavouriteItems.addFavouriteItem(favouriteItem)
        }
        "houses" -> {
            val favouriteItem = FavouriteItem(userId = user.id, houseId = itemId)
            daoFavouriteItems.addFavouriteItem(favouriteItem)
        }
        "movies" -> {
            val favouriteItem = FavouriteItem(userId = user.id, movieId = itemId)
            daoFavouriteItems.addFavouriteItem(favouriteItem)
        }
        "potions" -> {
            val favouriteItem = FavouriteItem(userId = user.id, potionId = itemId)
            daoFavouriteItems.addFavouriteItem(favouriteItem)
        }
        "spells" -> {
            val favouriteItem = FavouriteItem(userId = user.id, spellId = itemId)
            daoFavouriteItems.addFavouriteItem(favouriteItem)
        }
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

        else -> {
            return listOf()
        }
    }
}

fun <O> getItem(id: String): O {
    TODO("Not yet implemented")
}

fun removeItem(id: String) {
    TODO("Not yet implemented")
}
