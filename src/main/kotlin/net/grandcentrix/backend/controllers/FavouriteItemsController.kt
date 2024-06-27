package net.grandcentrix.backend.controllers

import io.ktor.server.application.*
import net.grandcentrix.backend.dao.daoUsers
import net.grandcentrix.backend.plugins.RequestException

fun ApplicationCall.addFavouriteItem(item: String, itemId: String, username: String) {

    val user = daoUsers.getItem(username)
        ?: throw RequestException("Not possible to add favourite item - user not found!")

    when (item) {
        "book" -> user.favouriteItems.books?.add(itemId)
        "character" -> user.favouriteItems.characters?.add(itemId)
        "house" -> user.favouriteItems.houses?.add(itemId)
        "movie" -> user.favouriteItems.movies?.add(itemId)
        "potion" -> user.favouriteItems.potions?.add(itemId)
        "spell" -> user.favouriteItems.spells?.add(itemId)
    }

    daoUsers.updateFavouriteItems(username, user.favouriteItems)
    print(user)
}

fun <O> getItem(id: String): O {
    TODO("Not yet implemented")
}

fun removeItem(id: String) {
    TODO("Not yet implemented")
}
