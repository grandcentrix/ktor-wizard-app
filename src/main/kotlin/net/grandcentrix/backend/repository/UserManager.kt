package net.grandcentrix.backend.repository

import kotlinx.serialization.json.Json
import kotlinx.serialization.json.encodeToJsonElement
import net.grandcentrix.backend.models.User
import java.io.File

class UserManager: RepositoryManager<User,String, List<User>,User?> {

    companion object {
        val UserManagerInstance: UserManager = UserManager()
    }

    private val usersFile = File("users.json")
    private val users = getAll().toMutableList()

    override fun getAll(): List<User> {
        if (!usersFile.exists()) {
            usersFile.createNewFile()
        }

        val fileText = usersFile.readText()
        if (fileText != "[]") {
            val usersList = Json.decodeFromString<List<User>>(fileText)

            return usersList
        }
        return listOf()
    }

    override fun getItem(name: String): User? = users.find { it.username == name }

    override fun deleteItem(name: String) {
        TODO("Not yet implemented")
    }

    override fun addItem(item: User) {
        users.add(item)
        val file = Json.encodeToJsonElement(users).toString()
        usersFile.writeText(file)
    }

    override fun updateItem(item: User) {
        TODO("Not yet implemented")
    }
}