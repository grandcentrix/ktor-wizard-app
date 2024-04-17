package net.grandcentrix.backend.repository

import kotlinx.serialization.json.Json
import kotlinx.serialization.json.encodeToJsonElement
import net.grandcentrix.backend.models.User
import java.io.File

class UserManager: RepositoryManager<User,String, List<User>,User?> {

    companion object {
        val UserManagerInstance: UserManager = UserManager()
    }

    private var users = listOf<User>()

    private fun getFile() = File("users.json")

    override fun getAll(): List<User> {
        if (!getFile().exists()) {
            getFile().createNewFile()
        }

        val fileText = getFile().readText()
        if (fileText != "[]") {
            val usersList = Json.decodeFromString<List<User>>(fileText)

            return usersList
        }
        return listOf()
    }

    override fun getItem(username: String): User? = getAll().find { it.username == username }

    fun getUserByEmail(email: String): User? = users.find { it.email == email }

    override fun deleteItem(name: String) {
        TODO("Not yet implemented")
    }

    override fun addItem(item: User) {
        users = getAll() + item
        val file = Json.encodeToJsonElement(users).toString()
        getFile().writeText(file)
    }

    override fun updateItem(item: User) {
        TODO("Not yet implemented")
    }
}