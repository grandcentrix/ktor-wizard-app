package net.grandcentrix.backend.dao

import net.grandcentrix.backend.models.User

interface DAOFacade {
    // return all elements
    fun getAll(): List<User>

    // return a single element
    fun getItem(name: String): User?

    //add element
    fun addItem(item: User)

    //update element
    fun updateItem(item: User)

    //delete element
    fun deleteItem(name: String)
//    abstract suspend fun getByEmail(email: N): E

}