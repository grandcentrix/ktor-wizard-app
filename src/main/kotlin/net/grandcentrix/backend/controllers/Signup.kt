package net.grandcentrix.backend.controllers

import io.ktor.http.*
import io.ktor.server.plugins.*
import net.grandcentrix.backend.models.User
import net.grandcentrix.backend.plugins.UserAlreadyExistsException
import net.grandcentrix.backend.repository.HouseManager.Companion.HouseManagerInstance
import net.grandcentrix.backend.repository.UserManager.Companion.UserManagerInstance

class Signup {

    companion object {
        val SignupInstance: Signup = Signup()
    }

    var status = ""

    fun createUser(formParameters: Parameters) {
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

        if (UserManagerInstance.getUserByEmail(email) != null) {
            status = "Email is already in use!"
            throw UserAlreadyExistsException("Email is already in use!")
        }

        if (UserManagerInstance.getUserByUsername(username) != null) {
            status = "Username is already in use!"
            throw UserAlreadyExistsException("Username is already in use!")
        }


        if (house.isNullOrBlank()) {
            val user = User(
                2, //TODO assign unique ID automatically
                name,
                surname,
                email,
                username,
                password,
                null
            )
            UserManagerInstance.addItem(user)
        } else {
            val user = User(
                2,
                name,
                surname,
                email,
                username,
                password,
                HouseManagerInstance.getItem(house)
            )
            UserManagerInstance.addItem(user)
        }

        status = "Account created with success!"

    }
}