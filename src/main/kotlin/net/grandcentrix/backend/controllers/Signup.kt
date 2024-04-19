package net.grandcentrix.backend.controllers

import io.ktor.http.*
import io.ktor.server.plugins.*
import net.grandcentrix.backend.dao.daoUsers
import net.grandcentrix.backend.models.User
import net.grandcentrix.backend.plugins.UserAlreadyExistsException
import net.grandcentrix.backend.repository.HouseManager.Companion.HouseManagerInstance

class Signup {

    companion object {
        val SignupInstance: Signup = Signup()
    }

    var status = ""

    suspend fun createUser(formParameters: Parameters) {

        val name = formParameters["name"]
        val surname = formParameters["surname"]
        val email = formParameters["email"]
        val username = formParameters["username"]
        val password = formParameters["password"]
        val house = formParameters["house"]

        if (
            name.isNullOrBlank() ||
            surname.isNullOrBlank() ||
            username.isNullOrBlank() ||
            password.isNullOrBlank() ||
            email.isNullOrBlank())
        {
            status = "Required fields cannot be empty!"
            throw MissingRequestParameterException("Missing required fields!")
        }

        val hashedPassword = password.hashCode()

        verifyDuplicates(email, username)

        if (house.isNullOrBlank()) {
            val user = User(
                2, //TODO assign unique ID automatically
                name,
                surname,
                email,
                username,
                hashedPassword,
                null
            )
            daoUsers.addItem(user)
        } else {
            val user = User(
                2,
                name,
                surname,
                email,
                username,
                hashedPassword,
                HouseManagerInstance.getItem(house)
            )
            daoUsers.addItem(user)
        }

        status = "Account created with success!"

    }

    private suspend fun verifyDuplicates(email: String, username: String) {
        if (daoUsers.getByEmail(email) != null) {
            status = "Email is already in use!"
            throw UserAlreadyExistsException("Email is already in use!")
        }

        if (daoUsers.getItem(username) != null) {
            status = "Username is already in use!"
            throw UserAlreadyExistsException("Username is already in use!")
        }
    }
}