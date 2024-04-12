package net.grandcentrix.backend.controllers

import io.ktor.server.auth.*
import kotlinx.serialization.json.Json
import net.grandcentrix.backend.models.User
import java.io.File


class Login {
    companion object {
        val LoginInstance: Login = Login()
    }

    var status = ""

    val usersFile = File("users.json")
    private val users = getUsers().toMutableList()

    fun getUsers(): List<User> {
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

    fun getUser(username: String): User? = getUsers().find { it.username == username }

//    fun verifyLogin(formParameters: Parameters): Boolean {
//        val username = formParameters.getOrFail("username")
//        val password = formParameters.getOrFail("password")
//            users.forEach {
//                if (it.username != username && it.password != password) {
//                    return false
//                }
//            }
//            return true
//    }

    fun verifyLogin(credential: UserPasswordCredential): Boolean {
        users.forEach {
            if (it.username != credential.name && it.password != credential.password) {
                LoginInstance.status = "Username and password didn't match!"
                return false
            }
        }
        LoginInstance.status = "Login successful"
        return true
    }

}

// stores the session data
data class UserSession(val name: String): Principal