package net.grandcentrix.backend.repository

import kotlinx.serialization.json.Json
import kotlinx.serialization.json.encodeToJsonElement
import net.grandcentrix.backend.controllers.Login.Companion.LoginInstance
import net.grandcentrix.backend.models.User
import java.io.File

class UserManager: RepositoryManager<User, List<User>,User> {

    companion object {
        val UserManagerInstance: UserManager = UserManager()
    }

    private val users = LoginInstance.getUsers().toMutableList()

    override fun getAll(): List<User> {
        TODO("Not yet implemented")
    }

    override fun deleteItem(item: User) {
        TODO("Not yet implemented")
    }

    override fun addItem(item: User) {
        users.add(item)
        val file = Json.encodeToJsonElement(users).toString()
        LoginInstance.usersFile.writeText(file)
    }

    override fun updateItem(item: User) {
        TODO("Not yet implemented")
    }

    override fun getItem(item: User): User {
        TODO("Not yet implemented")
    }
}