package net.grandcentrix.backend.controllers

interface FavouriteItemsFacade<O> {
    fun addItem(id: String)

    fun getItem(id: String): O

    fun removeItem(id: String)

}