package net.grandcentrix.backend.controllers

import net.grandcentrix.backend.dao.daoUsers

class FavouriteItems {

    fun addItem(item: String, itemId: String, username: String) {
        val test = mapOf(item to itemId)
        val user = daoUsers.getItem(username)!!
        user.favouriteItems.add(test.toString())

        daoUsers.updateItem(user)
    }
    fun <O> getItem(id: String): O {
        TODO("Not yet implemented")
    }

    fun removeItem(id: String) {
        TODO("Not yet implemented")
    }
}