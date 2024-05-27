package net.grandcentrix.backend.dao

import net.grandcentrix.backend.models.User

interface DAOFacade {
    // return all elements
    fun getAll(): List<User>

    // return a single element
    fun getItem(username: String): User?

    //add element
    fun addItem(user: User)

    //update element
    fun updateItem(user: User)

    //delete element
    fun deleteItem(username: String)

}